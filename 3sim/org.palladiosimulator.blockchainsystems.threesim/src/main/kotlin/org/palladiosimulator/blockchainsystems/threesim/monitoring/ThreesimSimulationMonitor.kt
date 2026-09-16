package org.palladiosimulator.blockchainsystems.threesim.monitoring

import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundAbandonedTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundPrivateBlockTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundReleasedTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundStartedTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningNodeBehavior
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockTypeChangedTraceEvent
import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin
import org.palladiosimulator.blockchainsystems.core.eventcoordination.SafetyTerminationListener
import org.palladiosimulator.blockchainsystems.core.scalability.ScalabilityStateTracker
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegions
import org.palladiosimulator.blockchainsystems.core.mining.BlockMinedTraceEvent
import org.palladiosimulator.blockchainsystems.core.monitoring.abstractions.SimulationMonitor
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockReceivedTraceEvent
import org.palladiosimulator.blockchainsystems.core.simulation.termination.InActivityThresholdCondition
import org.palladiosimulator.blockchainsystems.core.simulation.termination.LongestChainExceededMaxLengthCondition
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSubmittedTraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSubmissionProcess
import org.palladiosimulator.blockchainsystems.core.utils.CounterMap
import org.palladiosimulator.blockchainsystems.threesim.behavior.BlockUtils
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.TransactionThroughputCalculator
import org.palladiosimulator.blockchainsystems.threesim.utils.BlockchainSystemFailureLog
import org.palladiosimulator.blockchainsystems.threesim.utils.BlocksMap

/**
 * 3SIM monitor with optional warm-up / measurement / transaction-drain windows.
 *
 * When measuredBlocksPerValidator > 0, the legacy chain-length termination is
 * replaced by the refined study protocol:
 *   1. ignore kappa_warm * N_V canonical blocks;
 *   2. measure the next kappa_measure * N_V canonical blocks;
 *   3. stop accepting new transactions and follow measurement-window
 *      transactions for at most T_drain after their own submission time.
 */
class ThreesimSimulationMonitor(
  private val maxBlockchainLengthCondition: LongestChainExceededMaxLengthCondition,
  private val failureThroughputThreshold: Double,
  private val inactivityThresholdCondition: InActivityThresholdCondition,
  private val warmupBlocksPerValidator: Int = 0,
  private val measuredBlocksPerValidator: Int = 0,
  private val transactionDrainMillis: Long = 0L,
  private val retainTransactionFollowUpObservations: Boolean = false
) : SimulationMonitor, SafetyTerminationListener {

  private enum class Phase { LEGACY, WARMUP, MEASUREMENT, DRAIN }

  private lateinit var nodes: MutableSet<BlockchainSystemNode>
  private lateinit var geographicalRegions: GeographicalRegions
  private lateinit var transactionSubmissionProcess: TransactionSubmissionProcess
  private lateinit var simulationClock: SimulationClock
  private lateinit var nodeIndexById: Map<String, Int>

  private val refinedWindowEnabled: Boolean get() = measuredBlocksPerValidator > 0
  private var phase: Phase = Phase.LEGACY
  private var warmupTargetCanonicalBlocks = 0
  /** Unique blocks that have crossed the canonical-majority threshold during warm-up. */
  private val warmupCanonicalBlocksSeen = linkedSetOf<String>()
  /** Unique blocks that first cross the canonical-majority threshold during measurement. */
  private val measurementCanonicalBlocksSeen = linkedSetOf<String>()
  private var measurementTargetCanonicalBlocks = 0
  private var measurementStartTimeMs = 0L
  private var measurementEndTimeMs = 0L
  private var terminationReason: String = "NOT_TERMINATED"
  private var transactionFollowUpCompleted: Boolean = false

  /*
   * DIAGNOSTIC ONLY. When > 0, print canonical-chain progress every N newly
   * majority-visible canonical blocks. This does not change simulation state,
   * event scheduling, termination, or random-number consumption.
   *
   * Enable with:
   *   -Dthreesim.canonicalProgressEvery=10
   */
  private val canonicalProgressEvery: Int =
    Integer.getInteger("threesim.canonicalProgressEvery", 0)
  private var lastCanonicalProgressReported: Int = -1

  /*
   * DIAGNOSTIC ONLY. When > 0, print protocol progress every N block proposals.
   * This separates three possibilities in a long run: mining has stopped, the
   * local longest chains have stopped advancing, or majority-visible canonical
   * progress alone has stopped. No state, scheduling, or RNG use is changed.
   *
   * Enable with:
   *   -Dthreesim.blockProgressEvery=10
   */
  private val blockProgressEvery: Int =
    Integer.getInteger("threesim.blockProgressEvery", 0)

  /*
   * SAFETY WATCHDOG FOR REFINED WINDOWS. Disabled by default. When enabled,
   * terminate an execution as incomplete if the phase-local canonical-progress
   * high-water mark has not increased for the configured amount of simulated
   * time during WARMUP or MEASUREMENT. This does not alter consensus, fork
   * choice, mining, transaction processing, or random-number consumption.
   *
   * Configure only after the global simulated-time threshold has been
   * calibrated and frozen for the production experiment.
   */
  private val canonicalProgressStallMillis: Long =
    java.lang.Long.getLong("threesim.canonicalProgressStallMs", 0L).coerceAtLeast(0L)
  private var canonicalProgressHighWater: Int = 0
  private var canonicalProgressLastAdvanceTimeMs: Long = 0L
  private var maxCanonicalProgressGapMillisObserved: Long = 0L
  private var processedEventsObserved: Long = 0L
  private var maxFutureEventsObserved: Long = 0L
  private var scalabilityStateTracker: ScalabilityStateTracker? = null

  /*
   * MACHINE-INDEPENDENT SAFETY LIMITS. Disabled by default. These limits stop an
   * execution as scientifically incomplete before model-induced state growth can
   * exhaust the JVM. They do not drop transactions, cap mempools, change arrival
   * rates, or alter consensus. Calibrate once, freeze, and record them for the
   * production experiment.
   *
   *   -Dthreesim.maxTransactionSubmissions=...
   *   -Dthreesim.maxBlockProposals=...
   *   -Dthreesim.maxTransactionKnowledgeEntries=...
   *   -Dthreesim.maxBlockKnowledgeEntries=...
   *   -Dthreesim.maxMempoolEntries=...
   *   -Dthreesim.maxMeasurementTransactionEntries=...
   */
  private val maxTransactionSubmissions: Long =
    java.lang.Long.getLong("threesim.maxTransactionSubmissions", 0L).coerceAtLeast(0L)
  private val maxBlockProposals: Long =
    java.lang.Long.getLong("threesim.maxBlockProposals", 0L).coerceAtLeast(0L)
  private val maxFutureEvents: Long =
    java.lang.Long.getLong("threesim.maxFutureEvents", 0L).coerceAtLeast(0L)
  private val maxProcessedEvents: Long =
    java.lang.Long.getLong("threesim.maxProcessedEvents", 0L).coerceAtLeast(0L)

  /** Tracks majority-visible canonical-chain membership (Included or Confirmed) for window control. */
  private lateinit var canonicalProgressBlocks: BlocksMap
  /** Tracks blocks that reach the configured confirmation depth; used for transaction follow-up. */
  private lateinit var confirmationProgressBlocks: BlocksMap
  /** Canonical-chain blocks observed only inside the measurement window (Included or Confirmed). */
  private lateinit var canonicalMeasurementBlocks: BlocksMap

  private var numberOfSubmittedTransactions: Int = 0
  private var totalBlockProposalsAllPhases: Long = 0L
  private var totalTransactionSubmissionsAllPhases: Long = 0L
  private var blockReward: Double? = null
  private lateinit var blocksProposedPerNode: CounterMap<String>
  private lateinit var includedBlocks: BlocksMap
  private lateinit var confirmedBlocks: BlocksMap
  private lateinit var staleBlocks: BlocksMap
  private lateinit var forkedBlocks: BlocksMap

  /**
   * Flat append-only measurement-transaction table. The previous LinkedHashMap
   * retained one map node plus one observation object per submitted transaction.
   * High-load runs can contain millions of measurement transactions, so keep the
   * same exact txId/submission/confirmation information in flat arrays instead.
   */
  private class MeasurementTransactionStore(
    initialCapacity: Int = 16,
    private val beforeNewEntry: (() -> Unit)? = null,
    private val afterNewEntry: (() -> Unit)? = null,
    private val afterClear: (() -> Unit)? = null
  ) {
    private var keys: Array<String?> = arrayOfNulls(tableSizeFor(initialCapacity))
    private var submittedAt: LongArray = LongArray(keys.size)
    private var confirmedAt: LongArray = LongArray(keys.size) { UNCONFIRMED }
    private var resizeAt: Int = maxOf(1, (keys.size * LOAD_FACTOR).toInt())

    var size: Int = 0
      private set
    var unconfirmedCount: Int = 0
      private set
    var latestSubmittedAtMs: Long = 0L
      private set

    fun isEmpty(): Boolean = size == 0

    fun addIfAbsent(txId: String, submittedAtMs: Long): Boolean {
      var slot = spread(txId.hashCode()) and (keys.size - 1)
      while (true) {
        val current = keys[slot]
        if (current == null) break
        if (current == txId) return false
        slot = (slot + 1) and (keys.size - 1)
      }

      beforeNewEntry?.invoke()
      if (size + 1 > resizeAt) {
        resize(keys.size shl 1)
        slot = spread(txId.hashCode()) and (keys.size - 1)
        while (keys[slot] != null) {
          slot = (slot + 1) and (keys.size - 1)
        }
      }

      keys[slot] = txId
      submittedAt[slot] = submittedAtMs
      confirmedAt[slot] = UNCONFIRMED
      size++
      unconfirmedCount++
      afterNewEntry?.invoke()
      if (submittedAtMs > latestSubmittedAtMs) latestSubmittedAtMs = submittedAtMs
      return true
    }

    fun confirmIfEligible(txId: String, occurrenceTime: Long, drainMillis: Long) {
      val slot = findSlot(txId)
      if (slot < 0 || confirmedAt[slot] != UNCONFIRMED) return
      val deadline = submittedAt[slot] + drainMillis
      if (occurrenceTime <= deadline) {
        confirmedAt[slot] = occurrenceTime
        unconfirmedCount--
      }
    }

    fun forEach(action: (String, Long, Long?) -> Unit) {
      for (slot in keys.indices) {
        val txId = keys[slot] ?: continue
        val confirmation = confirmedAt[slot]
        action(txId, submittedAt[slot], if (confirmation == UNCONFIRMED) null else confirmation)
      }
    }

    fun clear() {
      keys.fill(null)
      submittedAt.fill(0L)
      confirmedAt.fill(UNCONFIRMED)
      size = 0
      unconfirmedCount = 0
      latestSubmittedAtMs = 0L
      afterClear?.invoke()
    }

    private fun findSlot(txId: String): Int {
      var slot = spread(txId.hashCode()) and (keys.size - 1)
      while (true) {
        val current = keys[slot] ?: return -1
        if (current == txId) return slot
        slot = (slot + 1) and (keys.size - 1)
      }
    }

    private fun resize(newCapacity: Int) {
      val oldKeys = keys
      val oldSubmittedAt = submittedAt
      val oldConfirmedAt = confirmedAt

      keys = arrayOfNulls(newCapacity)
      submittedAt = LongArray(newCapacity)
      confirmedAt = LongArray(newCapacity) { UNCONFIRMED }
      resizeAt = maxOf(1, (newCapacity * LOAD_FACTOR).toInt())

      for (oldSlot in oldKeys.indices) {
        val txId = oldKeys[oldSlot] ?: continue
        var newSlot = spread(txId.hashCode()) and (keys.size - 1)
        while (keys[newSlot] != null) {
          newSlot = (newSlot + 1) and (keys.size - 1)
        }
        keys[newSlot] = txId
        submittedAt[newSlot] = oldSubmittedAt[oldSlot]
        confirmedAt[newSlot] = oldConfirmedAt[oldSlot]
      }
    }

    private companion object {
      const val UNCONFIRMED: Long = Long.MIN_VALUE
      const val LOAD_FACTOR: Double = 0.65

      fun spread(hashCode: Int): Int = hashCode xor (hashCode ushr 16)

      fun tableSizeFor(requested: Int): Int {
        var capacity = 16
        val target = maxOf(1, requested)
        while (capacity < target / LOAD_FACTOR) capacity = capacity shl 1
        return capacity
      }
    }
  }

  private val measurementTransactions = MeasurementTransactionStore(
    beforeNewEntry = { scalabilityStateTracker?.beforeMeasurementTransactionEntryAdded() },
    afterNewEntry = { scalabilityStateTracker?.onMeasurementTransactionEntryAdded() },
    afterClear = { scalabilityStateTracker?.resetMeasurementTransactionEntries() }
  )

  /**
   * Follow-up diagnostic for P-B. Receiver timestamps are kept in a primitive
   * array only until T90 is known; then the array is released immediately.
   */
  private class MeasurementBlockPropagationObservation(
    val minedTimeMs: Long,
    val minerNodeId: String?,
    validatorCount: Int
  ) {
    var firstReceiptTimeByNode: LongArray? = LongArray(validatorCount) { UNSEEN_RECEIPT_TIME }
    var receiverCount: Int = 0
    var t90Ms: Long? = null
  }

  private val measurementBlockPropagation =
    HashMap<String, MeasurementBlockPropagationObservation>()

  private class RunningAverage {
    private var count: Long = 0L
    private var sum: Double = 0.0

    fun add(value: Double) {
      sum += value
      count++
    }

    fun clear() {
      count = 0L
      sum = 0.0
    }

    fun averageOr(defaultValue: Double): Double = if (count == 0L) defaultValue else sum / count.toDouble()
  }

  private val failureLog = BlockchainSystemFailureLog()
  private val throughputsDuringFailure = RunningAverage()
  private val confirmationLatenciesDuringFailure = RunningAverage()
  private val throughputsWithoutFailure = RunningAverage()
  private val confirmationLatenciesWithoutFailure = RunningAverage()
  private var lastThroughputCheckTimestamp: Long = 0

  /** Runtime attack-round tracker for the revised paper-defined SPSM. */
  private lateinit var selfishMiningAttackRoundTracker: SelfishMiningAttackRoundTracker

  override fun initialize(blockchainSystem: BlockchainSystem) {
    require(warmupBlocksPerValidator >= 0) { "warmupBlocksPerValidator must be >= 0" }
    require(measuredBlocksPerValidator >= 0) { "measuredBlocksPerValidator must be >= 0" }
    require(transactionDrainMillis >= 0L) { "transactionDrainMillis must be >= 0" }

    nodes = blockchainSystem.nodes
    nodeIndexById = nodes.sortedBy { it.id }.mapIndexed { index, node -> node.id to index }.toMap()
    geographicalRegions = blockchainSystem.geographicalRegions
    transactionSubmissionProcess = blockchainSystem.transactionSubmissionProcess
    blockReward = blockchainSystem.blockReward

    val attackerNodeIds = nodes
      .filter { it.behavior is SelfishMiningNodeBehavior }
      .map { it.id }
      .toSet()
    val honestNodeIds = nodes.map { it.id }.toSet() - attackerNodeIds
    selfishMiningAttackRoundTracker = SelfishMiningAttackRoundTracker(attackerNodeIds, honestNodeIds)

    blocksProposedPerNode = CounterMap.create(nodes.map { it.id })
    val majorityThreshold = calculateMajorityThreshold()
    includedBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    confirmedBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    staleBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    forkedBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    canonicalProgressBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    confirmationProgressBlocks = BlocksMap(majorityThreshold, nodeIndexById)
    canonicalMeasurementBlocks = BlocksMap(majorityThreshold, nodeIndexById)

    if (refinedWindowEnabled) {
      warmupCanonicalBlocksSeen.clear()
      measurementCanonicalBlocksSeen.clear()
      warmupTargetCanonicalBlocks = warmupBlocksPerValidator * nodes.size
      measurementTargetCanonicalBlocks = warmupTargetCanonicalBlocks + measuredBlocksPerValidator * nodes.size
      if (warmupTargetCanonicalBlocks > 0) {
        phase = Phase.WARMUP
      } else {
        phase = Phase.MEASUREMENT
        measurementStartTimeMs = 0L
      }
    } else {
      phase = Phase.LEGACY
    }
  }

  fun getFinalState(finalSystemTime: Long): ThreesimSimulationMonitorState {
    observeCanonicalProgressGap(finalSystemTime)
    val effectiveEnd = if (measurementEndTimeMs > 0L) measurementEndTimeMs else finalSystemTime
    val measurementDuration = if (refinedWindowEnabled) {
      (effectiveEnd - measurementStartTimeMs).coerceAtLeast(0L)
    } else finalSystemTime
    val spsmSummary = selfishMiningAttackRoundTracker.summary()
    val followUpSummary = buildTransactionFollowUpSummary(finalSystemTime)
    val propagationSummary = buildBlockPropagationSummary()
    val scalability = scalabilityStateTracker?.snapshot()

    return ThreesimSimulationMonitorState(
      numberOfNodes = nodes.size,
      hashPowerPerNode = calculateHashPowerPerNode(),
      blocksProposedPerNode = calculateBlocksProposedPerNode(),
      canonicalBlocksPerNode = calculateCanonicalBlocksPerNode(),
      numberOfGeographicalRegions = calculateNumberOfGeographicalRegions(),
      numberOfNodesPerRegion = calculateNumberOfNodesPerRegion(),
      numberOfSubmittedTransactions = numberOfSubmittedTransactions,
      numberOfConfirmedTransactions = calculateNumberOfConfirmedTransactions(),
      blockProposalTimeAndConfirmationTimePerConfirmedBlock = calculateBlockProposalTimeAndConfirmationTimePerConfirmedBlock(),
      meanTimeBetweenFailures = calculateMeanTimeBetweenFailures(measurementDuration),
      meanTimeToRepair = calculateMeanTimeToRepair(),
      numberOfStaleBlocks = calculateNumberOfStaleBlocks(),
      numberOfConfirmedBlocks = calculateNumberOfConfirmedBlocks(),
      numberOfCanonicalBlocks = calculateNumberOfCanonicalBlocks(),
      tokensHeldPerNode = calculateTokensHeldPerNode(),
      averageThroughputDuringFailure = calculateAverageThroughputDuringFailure(),
      averageThroughputDuringNormalOperation = calculateAverageThroughputWithoutFailure(),
      averageConfirmationLatencyDuringFailure = calculateAverageConfirmationLatencyDuringFailure(),
      averageConfirmationLatencyDuringNormalOperation = calculateAverageConfirmationLatencyWithoutFailure(),
      refinedWindowEnabled = refinedWindowEnabled,
      warmupTargetCanonicalBlocks = warmupTargetCanonicalBlocks,
      measurementTargetCanonicalBlocks = measurementTargetCanonicalBlocks,
      canonicalBlocksObservedForWindowControl = canonicalBlocksObservedForWindowControl(),
      measurementStartTimeMs = measurementStartTimeMs,
      // Preserve 0 when the requested measurement window did not complete.
      // `measurementDurationMs` may still use finalSystemTime for diagnostics, but
      // downstream analysis must be able to distinguish an incomplete window.
      measurementEndTimeMs = measurementEndTimeMs,
      measurementDurationMs = measurementDuration,
      transactionFollowUpObservationCount = followUpSummary.count,
      measurementTransactionsConfirmedWithinDrain = followUpSummary.confirmed,
      measurementTransactionsRightCensored = followUpSummary.censored,
      transactionConfirmationRatio = followUpSummary.confirmationRatio,
      restrictedMeanConfirmationLatencyMs = followUpSummary.restrictedMeanLatencyMs,
      transactionFollowUpObservationsRetained = retainTransactionFollowUpObservations,
      transactionFollowUpObservations = followUpSummary.retainedObservations,
      executionPhaseAtTermination = phase.name,
      terminationReason = terminationReason,
      transactionFollowUpCompleted = transactionFollowUpCompleted,
      totalBlockProposalsAllPhases = totalBlockProposalsAllPhases,
      totalTransactionSubmissionsAllPhases = totalTransactionSubmissionsAllPhases,
      maxCanonicalProgressGapMillisObserved = maxCanonicalProgressGapMillisObserved,
      processedEventsObserved = processedEventsObserved,
      maxFutureEventsObserved = maxFutureEventsObserved,
      currentTransactionKnowledgeEntries = scalability?.currentTransactionKnowledgeEntries ?: 0L,
      maxTransactionKnowledgeEntriesObserved = scalability?.maxTransactionKnowledgeEntriesObserved ?: 0L,
      maxTransactionKnowledgeEntriesPerNodeObserved = scalability?.maxTransactionKnowledgeEntriesPerNodeObserved ?: 0L,
      currentBlockKnowledgeEntries = scalability?.currentBlockKnowledgeEntries ?: 0L,
      maxBlockKnowledgeEntriesObserved = scalability?.maxBlockKnowledgeEntriesObserved ?: 0L,
      maxBlockKnowledgeEntriesPerNodeObserved = scalability?.maxBlockKnowledgeEntriesPerNodeObserved ?: 0L,
      currentMempoolEntries = scalability?.currentMempoolEntries ?: 0L,
      maxMempoolEntriesObserved = scalability?.maxMempoolEntriesObserved ?: 0L,
      maxMempoolEntriesPerNodeObserved = scalability?.maxMempoolEntriesPerNodeObserved ?: 0L,
      currentMeasurementTransactionEntries = scalability?.currentMeasurementTransactionEntries ?: 0L,
      maxMeasurementTransactionEntriesObserved = scalability?.maxMeasurementTransactionEntriesObserved ?: 0L,
      canonicalProgressStallMillis = canonicalProgressStallMillis,
      maxTransactionSubmissions = maxTransactionSubmissions,
      maxBlockProposals = maxBlockProposals,
      maxFutureEvents = maxFutureEvents,
      maxProcessedEvents = maxProcessedEvents,
      maxTransactionKnowledgeEntries = scalability?.maxTransactionKnowledgeEntries ?: 0L,
      maxBlockKnowledgeEntries = scalability?.maxBlockKnowledgeEntries ?: 0L,
      maxMempoolEntries = scalability?.maxMempoolEntries ?: 0L,
      maxMeasurementTransactionEntries = scalability?.maxMeasurementTransactionEntries ?: 0L,
      blockRateObservationTimeMs = finalSystemTime,
      transactionRateObservationTimeMs = if (measurementEndTimeMs > 0L) measurementEndTimeMs else finalSystemTime,
      measurementBlocksEligibleForPropagationT90 = propagationSummary.eligibleBlocks,
      measurementBlocksReachingPropagationT90 = propagationSummary.blocksReachingT90,
      meanBlockPropagationT90Ms = propagationSummary.meanT90Ms,
      medianBlockPropagationT90Ms = propagationSummary.medianT90Ms,
      selfishMiningAttackRoundsStarted = spsmSummary.startedRounds,
      successfulSelfishMiningAttackRounds = spsmSummary.successfulRounds,
      failedSelfishMiningAttackRounds = spsmSummary.failedRounds,
      ambiguousSelfishMiningAttackRounds = spsmSummary.ambiguousRounds,
      unambiguousSelfishMiningAttackRounds = spsmSummary.unambiguousRounds,
      selfishMiningSuccessProbability = spsmSummary.successProbability
    )
  }

  private fun addMeasurementBlock(blockType: BlockType, block: Block, nodeId: String, occurrenceTime: Long): Boolean {
    return when (blockType) {
      BlockType.IncludedBlock -> includedBlocks
      BlockType.ConfirmedBlock -> confirmedBlocks
      BlockType.StaleBlock -> staleBlocks
      BlockType.ForkingBlock -> forkedBlocks
    }.addNodeToBlock(block, nodeId, occurrenceTime)
  }

  private fun removeMeasurementBlock(blockType: BlockType, blockHash: String, nodeId: String) {
    when (blockType) {
      BlockType.IncludedBlock -> includedBlocks
      BlockType.ConfirmedBlock -> confirmedBlocks
      BlockType.StaleBlock -> staleBlocks
      BlockType.ForkingBlock -> forkedBlocks
    }.removeNodeFromBlock(blockHash, nodeId)
  }

  override fun onTraceEventOccurred(event: TraceEvent, logOrigin: TraceEventLogOrigin) {
    inactivityThresholdCondition.restartLoggedSimulationClock()

    when (event.eventType) {
      SelfishMiningAttackRoundStartedTraceEvent.EVENT_TYPE -> {
        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onRoundStarted(event as SelfishMiningAttackRoundStartedTraceEvent)
        }
      }

      SelfishMiningAttackRoundPrivateBlockTraceEvent.EVENT_TYPE -> {
        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onPrivateBlock(event as SelfishMiningAttackRoundPrivateBlockTraceEvent)
        }
      }

      SelfishMiningAttackRoundReleasedTraceEvent.EVENT_TYPE -> {
        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onRoundReleased(event as SelfishMiningAttackRoundReleasedTraceEvent)
        }
      }

      SelfishMiningAttackRoundAbandonedTraceEvent.EVENT_TYPE -> {
        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onRoundAbandoned(event as SelfishMiningAttackRoundAbandonedTraceEvent)
        }
      }

      BlockMinedTraceEvent.EVENT_TYPE -> {
        val e = event as BlockMinedTraceEvent
        totalBlockProposalsAllPhases++

        if (phase == Phase.MEASUREMENT) {
          measurementBlockPropagation.putIfAbsent(
            e.block.hash,
            MeasurementBlockPropagationObservation(
              minedTimeMs = e.block.blockMinedTimestamp,
              minerNodeId = e.block.originId,
              validatorCount = nodes.size
            )
          )
        }

        if (
          blockProgressEvery > 0 &&
          totalBlockProposalsAllPhases % blockProgressEvery.toLong() == 0L
        ) {
          System.err.println(
            "[3SIM-block-progress] phase=${phase.name} " +
              "proposals=$totalBlockProposalsAllPhases " +
              "longestChain=${maxBlockchainLengthCondition.currentLength} " +
              "canonical=${canonicalProgressBlocks.getNumberOfValidBlocks()} " +
              "submittedTxAllPhases=$totalTransactionSubmissionsAllPhases " +
              "timeMs=${e.occurrenceTime}"
          )
        }

        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          if (BlockUtils.isBlockForked(e.block)) {
            forkedBlocks.addNodeToBlock(e.block, logOrigin.id, e.occurrenceTime)
          }
          blocksProposedPerNode.increment(logOrigin.id)
        }
      }

      BlockReceivedTraceEvent.EVENT_TYPE -> {
        val e = event as BlockReceivedTraceEvent
        val block = e.sentBlock
        if (block != null) {
          val observation = measurementBlockPropagation[block.hash]
          if (observation != null && logOrigin.id != observation.minerNodeId && observation.t90Ms == null) {
            val receiptTimes = observation.firstReceiptTimeByNode
            val nodeIndex = nodeIndexById[logOrigin.id]
            if (receiptTimes != null && nodeIndex != null && receiptTimes[nodeIndex] == UNSEEN_RECEIPT_TIME) {
              receiptTimes[nodeIndex] = e.occurrenceTime
              observation.receiverCount++

              // T90 is defined over non-miner validating nodes.
              val nonMinerValidatorCount = (nodes.size - 1).coerceAtLeast(0)
              val requiredReceivers = (9 * nonMinerValidatorCount + 9) / 10
              if (requiredReceivers > 0 && observation.receiverCount >= requiredReceivers) {
                val observedTimes = LongArray(observation.receiverCount)
                var destination = 0
                for (time in receiptTimes) {
                  if (time != UNSEEN_RECEIPT_TIME) observedTimes[destination++] = time
                }
                observedTimes.sort()
                observation.t90Ms =
                  (observedTimes[requiredReceivers - 1] - observation.minedTimeMs).coerceAtLeast(0L)
                // No later receipt can change T90. Release the O(N_V) array now.
                observation.firstReceiptTimeByNode = null
              }
            }
          }
        }
      }

      BlockAppendedTraceEvent.EVENT_TYPE -> {
        val e = event as BlockAppendedTraceEvent
        maxBlockchainLengthCondition.onBlockAppended(e.blockPosition)

        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onBlockAppended(
            e.appendedBlock.hash, e.appendedBlockType, logOrigin.id, e.occurrenceTime
          )
        }

        val canonicalBecameValid = if (phase != Phase.DRAIN && isCanonicalType(e.appendedBlockType)) {
          canonicalProgressBlocks.addNodeToBlock(e.appendedBlock, logOrigin.id, e.occurrenceTime)
        } else false
        if (phase == Phase.WARMUP && canonicalBecameValid) {
          warmupCanonicalBlocksSeen.add(e.appendedBlock.hash)
        }
        val confirmationBecameValid = if (e.appendedBlockType == BlockType.ConfirmedBlock) {
          confirmationProgressBlocks.addNodeToBlock(e.appendedBlock, logOrigin.id, e.occurrenceTime)
        } else false

        var measurementCanonicalBecameValid = false
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          val measurementBecameValid = addMeasurementBlock(
            e.appendedBlockType, e.appendedBlock, logOrigin.id, e.occurrenceTime)
          if (phase == Phase.MEASUREMENT && isCanonicalType(e.appendedBlockType)) {
            val crossedMajority = canonicalMeasurementBlocks.addNodeToBlock(
              e.appendedBlock, logOrigin.id, e.occurrenceTime)
            measurementCanonicalBecameValid =
              crossedMajority &&
              e.appendedBlock.hash !in warmupCanonicalBlocksSeen &&
              measurementCanonicalBlocksSeen.add(e.appendedBlock.hash)
          }
          if (measurementBecameValid && e.appendedBlockType == BlockType.ConfirmedBlock) {
            monitorThroughputForNewlyConfirmedBlock(e.appendedBlock, e.occurrenceTime)
          }
        }

        if (confirmationBecameValid) {
          recordMeasurementTransactionConfirmations(e.appendedBlock, e.occurrenceTime)
        }
        if (canonicalBecameValid || measurementCanonicalBecameValid) {
          updateRefinedPhaseAfterCanonicalProgress(e.occurrenceTime)
        }
      }

      BlockTypeChangedTraceEvent.EVENT_TYPE -> {
        val e = event as BlockTypeChangedTraceEvent
        val nodeId = logOrigin.id

        if (isSpsmObservationPhase()) {
          selfishMiningAttackRoundTracker.onBlockTypeChanged(
            e.block.hash, e.oldBlockType, e.newBlockType, nodeId, e.occurrenceTime
          )
        }

        val oldCanonical = isCanonicalType(e.oldBlockType)
        val newCanonical = isCanonicalType(e.newBlockType)
        var canonicalBecameValid = false
        if (phase != Phase.DRAIN) {
          if (oldCanonical && !newCanonical) {
            canonicalProgressBlocks.removeNodeFromBlock(e.block.hash, nodeId)
          } else if (!oldCanonical && newCanonical) {
            canonicalBecameValid = canonicalProgressBlocks.addNodeToBlock(e.block, nodeId, e.occurrenceTime)
          }
        }
        if (phase == Phase.WARMUP && canonicalBecameValid) {
          warmupCanonicalBlocksSeen.add(e.block.hash)
        }

        if (e.oldBlockType == BlockType.ConfirmedBlock && e.newBlockType != BlockType.ConfirmedBlock) {
          confirmationProgressBlocks.removeNodeFromBlock(e.block.hash, nodeId)
        }
        val confirmationBecameValid = if (
          e.oldBlockType != BlockType.ConfirmedBlock && e.newBlockType == BlockType.ConfirmedBlock
        ) {
          confirmationProgressBlocks.addNodeToBlock(e.block, nodeId, e.occurrenceTime)
        } else false

        // Freeze block-based measurement metrics at the exact end of the
        // measurement window. During DRAIN we continue only transaction
        // follow-up; later reclassifications must not change H_prop, canonical
        // HHI, TPS, or SBR after their measurement denominator has stopped.
        var measurementCanonicalBecameValid = false
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          removeMeasurementBlock(e.oldBlockType, e.block.hash, nodeId)
          val measurementBecameValid = addMeasurementBlock(e.newBlockType, e.block, nodeId, e.occurrenceTime)

          if (phase == Phase.MEASUREMENT) {
            if (oldCanonical && !newCanonical) {
              canonicalMeasurementBlocks.removeNodeFromBlock(e.block.hash, nodeId)
            } else if (!oldCanonical && newCanonical) {
              val crossedMajority = canonicalMeasurementBlocks.addNodeToBlock(
                e.block, nodeId, e.occurrenceTime)
              measurementCanonicalBecameValid =
                crossedMajority &&
                e.block.hash !in warmupCanonicalBlocksSeen &&
                measurementCanonicalBlocksSeen.add(e.block.hash)
            }
          }

          if (measurementBecameValid && e.newBlockType == BlockType.ConfirmedBlock) {
            monitorThroughputForNewlyConfirmedBlock(e.block, e.occurrenceTime)
          }
        }

        if (confirmationBecameValid) {
          recordMeasurementTransactionConfirmations(e.block, e.occurrenceTime)
        }
        if (canonicalBecameValid || measurementCanonicalBecameValid) {
          updateRefinedPhaseAfterCanonicalProgress(e.occurrenceTime)
        }
      }

      TransactionSubmittedTraceEvent.EVENT_TYPE -> {
        val e = event as TransactionSubmittedTraceEvent
        totalTransactionSubmissionsAllPhases++
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          numberOfSubmittedTransactions++
          if (phase == Phase.MEASUREMENT) {
            measurementTransactions.addIfAbsent(
              e.transaction.txId,
              e.transaction.creationTime
            )
          }
        }
      }
    }
  }

  private data class BlockPropagationSummary(
    val eligibleBlocks: Int,
    val blocksReachingT90: Int,
    val meanT90Ms: Double?,
    val medianT90Ms: Double?
  )

  private fun buildBlockPropagationSummary(): BlockPropagationSummary {
    val t90Values = measurementBlockPropagation.values
      .mapNotNull { it.t90Ms }
      .sorted()

    val mean = if (t90Values.isEmpty()) null else t90Values.average()
    val median = if (t90Values.isEmpty()) {
      null
    } else {
      val middle = t90Values.size / 2
      if (t90Values.size % 2 == 1) {
        t90Values[middle].toDouble()
      } else {
        (t90Values[middle - 1].toDouble() + t90Values[middle].toDouble()) / 2.0
      }
    }

    return BlockPropagationSummary(
      eligibleBlocks = measurementBlockPropagation.size,
      blocksReachingT90 = t90Values.size,
      meanT90Ms = mean,
      medianT90Ms = median
    )
  }

  private fun updateRefinedPhaseAfterCanonicalProgress(occurrenceTime: Long) {
    if (!refinedWindowEnabled) return
    val canonicalCount = if (phase == Phase.WARMUP) {
      warmupCanonicalBlocksSeen.size
    } else {
      warmupTargetCanonicalBlocks + measurementCanonicalBlocksSeen.size
    }

    // Track a high-water mark, not the instantaneous canonical count: a reorg
    // may legitimately reduce the latter and must not be mistaken for progress.
    if (canonicalCount > canonicalProgressHighWater) {
      observeCanonicalProgressGap(occurrenceTime)
      canonicalProgressHighWater = canonicalCount
      canonicalProgressLastAdvanceTimeMs = occurrenceTime
    }

    if (
      canonicalProgressEvery > 0 &&
      canonicalCount > 0 &&
      canonicalCount != lastCanonicalProgressReported &&
      canonicalCount % canonicalProgressEvery == 0
    ) {
      lastCanonicalProgressReported = canonicalCount
      System.err.println(
        "[3SIM-chain-progress] phase=${phase.name} " +
          "canonical=$canonicalCount " +
          "warmupTarget=$warmupTargetCanonicalBlocks " +
          "measurementTarget=$measurementTargetCanonicalBlocks " +
          "submittedTxAllPhases=$totalTransactionSubmissionsAllPhases"
      )
    }

    if (phase == Phase.WARMUP && canonicalCount >= warmupTargetCanonicalBlocks) {
      beginMeasurement(occurrenceTime)
      return
    }

    // Warm-up progress is controlled by the cumulative canonical set, while
    // measurement progress is controlled by the measurement-local canonical set.
    // Using the cumulative count for both phases allowed a reorganization across
    // the phase boundary to produce 79 or 81 measured canonical blocks instead
    // of exactly kappa_measure * N_V.
    val measurementCanonicalTarget = measuredBlocksPerValidator * nodes.size
    if (
      phase == Phase.MEASUREMENT &&
      measurementCanonicalBlocksSeen.size >= measurementCanonicalTarget
    ) {
      measurementEndTimeMs = occurrenceTime
      phase = Phase.DRAIN
      transactionSubmissionProcess.stopTransactionSubmissionProcess()
    }
  }

  private fun beginMeasurement(occurrenceTime: Long) {
    phase = Phase.MEASUREMENT
    measurementStartTimeMs = occurrenceTime
    canonicalProgressHighWater = warmupTargetCanonicalBlocks
    canonicalProgressLastAdvanceTimeMs = occurrenceTime
    numberOfSubmittedTransactions = 0
    blocksProposedPerNode = CounterMap.create(nodes.map { it.id })
    includedBlocks.clear()
    confirmedBlocks.clear()
    canonicalMeasurementBlocks.clear()
    measurementCanonicalBlocksSeen.clear()
    staleBlocks.clear()
    forkedBlocks.clear()
    measurementTransactions.clear()
    selfishMiningAttackRoundTracker.reset()
    throughputsDuringFailure.clear()
    confirmationLatenciesDuringFailure.clear()
    throughputsWithoutFailure.clear()
    confirmationLatenciesWithoutFailure.clear()
    lastThroughputCheckTimestamp = occurrenceTime
  }

  private fun recordMeasurementTransactionConfirmations(block: Block, occurrenceTime: Long) {
    if (!refinedWindowEnabled || (phase != Phase.MEASUREMENT && phase != Phase.DRAIN)) return
    for (transaction in block.transactions) {
      measurementTransactions.confirmIfEligible(
        transaction.txId,
        occurrenceTime,
        transactionDrainMillis
      )
    }
  }

  /**
   * Sufficient statistics for the transaction follow-up analysis.
   *
   * Production scalability note:
   * A high-demand P01 run contained more than one million measurement-window
   * transactions. Serializing one TransactionFollowUpObservation per transaction
   * made a single result JSON millions of lines long even though the paper uses
   * only TCR, censoring counts and restricted mean confirmation latency.
   *
   * This method therefore computes those statistics directly from the complete
   * measurement population and creates individual observation objects only when
   * the explicit diagnostic flag is enabled.
   */
  private data class TransactionFollowUpSummary(
    val count: Int,
    val confirmed: Int,
    val censored: Int,
    val confirmationRatio: Double?,
    val restrictedMeanLatencyMs: Double?,
    val retainedObservations: List<TransactionFollowUpObservation>
  )

  private fun buildTransactionFollowUpSummary(finalSystemTime: Long): TransactionFollowUpSummary {
    if (!refinedWindowEnabled || measurementTransactions.isEmpty()) {
      return TransactionFollowUpSummary(
        count = 0,
        confirmed = 0,
        censored = 0,
        confirmationRatio = null,
        restrictedMeanLatencyMs = null,
        retainedObservations = emptyList()
      )
    }

    /*
     * Encode follow-up time and event/censor type in one primitive long and sort
     * that array for the Kaplan-Meier RMCL. This preserves the exact grouped
     * calculation without allocating a TreeMap node and group object for every
     * distinct follow-up timestamp.
     */
    val total = measurementTransactions.size
    val encodedFollowUp = LongArray(total)
    val retained = if (retainTransactionFollowUpObservations) {
      ArrayList<TransactionFollowUpObservation>(total)
    } else {
      null
    }

    var confirmedCount = 0
    var destination = 0

    measurementTransactions.forEach { txId, submittedAt, confirmation ->
      val deadline = submittedAt + transactionDrainMillis
      val confirmed = confirmation != null && confirmation <= deadline
      val followUp = if (confirmed) {
        confirmedCount++
        (confirmation!! - submittedAt).coerceAtLeast(0L)
      } else {
        // Right-censored at T_drain. If the run stopped earlier for another
        // reason, preserve the actually observed shorter follow-up interval.
        minOf(transactionDrainMillis, (finalSystemTime - submittedAt).coerceAtLeast(0L))
      }

      require(followUp <= (Long.MAX_VALUE ushr 1)) { "Follow-up interval too large to encode" }
      encodedFollowUp[destination++] = (followUp shl 1) or if (confirmed) 1L else 0L

      if (retained != null) {
        retained.add(
          TransactionFollowUpObservation(
            transactionId = txId,
            submissionTimeMs = submittedAt,
            followUpTimeMs = followUp,
            confirmed = confirmed
          )
        )
      }
    }

    encodedFollowUp.sort()
    val censoredCount = total - confirmedCount
    val ratio = confirmedCount.toDouble() / total.toDouble()
    val rmcl = restrictedMeanFromEncodedFollowUp(encodedFollowUp, total, transactionDrainMillis)

    return TransactionFollowUpSummary(
      count = total,
      confirmed = confirmedCount,
      censored = censoredCount,
      confirmationRatio = ratio,
      restrictedMeanLatencyMs = rmcl,
      retainedObservations = retained ?: emptyList()
    )
  }

  /**
   * Kaplan-Meier restricted mean survival/confirmation latency through tau.
   * The encoded array contains the complete follow-up population, so this result
   * is numerically equivalent to the former grouped-map implementation.
   */
  private fun restrictedMeanFromEncodedFollowUp(
    encodedFollowUp: LongArray,
    populationSize: Int,
    tau: Long
  ): Double? {
    if (populationSize <= 0 || tau <= 0L) return null

    var atRisk = populationSize
    var survival = 1.0
    var previousTime = 0L
    var area = 0.0
    var index = 0

    while (index < encodedFollowUp.size) {
      val time = (encodedFollowUp[index] ushr 1).coerceIn(0L, tau)
      var events = 0
      var censored = 0

      while (index < encodedFollowUp.size && (encodedFollowUp[index] ushr 1).coerceIn(0L, tau) == time) {
        if ((encodedFollowUp[index] and 1L) == 1L) events++ else censored++
        index++
      }

      if (time > previousTime) {
        area += survival * (time - previousTime).toDouble()
      }
      if (atRisk > 0 && events > 0) {
        survival *= 1.0 - events.toDouble() / atRisk.toDouble()
      }
      atRisk -= events + censored
      previousTime = time
      if (time >= tau) break
    }

    if (previousTime < tau) {
      area += survival * (tau - previousTime).toDouble()
    }
    return area
  }

  /**
   * Window-control progress on the same cumulative scale as
   * measurementTargetCanonicalBlocks. During measurement, use the
   * measurement-local canonical set so cross-boundary reorganizations cannot
   * change the requested measurement sample size.
   */
  private fun canonicalBlocksObservedForWindowControl(): Int = when (phase) {
    Phase.WARMUP -> warmupCanonicalBlocksSeen.size
    Phase.MEASUREMENT, Phase.DRAIN ->
      warmupTargetCanonicalBlocks + measurementCanonicalBlocksSeen.size
    Phase.LEGACY -> canonicalProgressBlocks.getNumberOfValidBlocks()
  }

  private fun isSpsmObservationPhase(): Boolean = phase == Phase.LEGACY || phase == Phase.MEASUREMENT

  private fun observeCanonicalProgressGap(currentTimeMs: Long) {
    if (phase != Phase.WARMUP && phase != Phase.MEASUREMENT) return

    val gapMillis =
      (currentTimeMs - canonicalProgressLastAdvanceTimeMs).coerceAtLeast(0L)
    if (gapMillis > maxCanonicalProgressGapMillisObserved) {
      maxCanonicalProgressGapMillisObserved = gapMillis
    }
  }

  override fun onSafetyTermination(reason: String) {
    markTermination(reason, false)
  }

  override fun onEventCoordinatorTelemetry(
    processedEvents: Long,
    maxFutureEventsObserved: Long
  ) {
    processedEventsObserved = maxOf(processedEventsObserved, processedEvents)
    this.maxFutureEventsObserved = maxOf(
      this.maxFutureEventsObserved,
      maxFutureEventsObserved
    )
  }

  override fun shouldTerminate(): Boolean {
    observeCanonicalProgressGap(simulationClock.currentTime)
    if (maxTransactionSubmissions > 0L && totalTransactionSubmissionsAllPhases >= maxTransactionSubmissions) {
      reportWorkloadLimit("TRANSACTION_SUBMISSIONS", maxTransactionSubmissions)
      return markTermination("WORKLOAD_LIMIT_TRANSACTION_SUBMISSIONS", false)
    }

    if (maxBlockProposals > 0L && totalBlockProposalsAllPhases >= maxBlockProposals) {
      reportWorkloadLimit("BLOCK_PROPOSALS", maxBlockProposals)
      return markTermination("WORKLOAD_LIMIT_BLOCK_PROPOSALS", false)
    }

    if (!refinedWindowEnabled) {
      if (inactivityThresholdCondition.hasProlongedInactivityExceeded()) {
        return markTermination("INACTIVITY", false)
      }
      if (maxBlockchainLengthCondition.hasLengthExceeded()) {
        return markTermination("LEGACY_CHAIN_LENGTH", true)
      }
      return false
    }

    // A completed drain has precedence over the generic inactivity condition.
    // This avoids classifying a normally completed pilot as incomplete merely
    // because no further events are expected after its follow-up deadline.
    if (phase == Phase.DRAIN) {
      if (measurementTransactions.isEmpty()) {
        return markTermination("DRAIN_NO_MEASUREMENT_TRANSACTIONS", true)
      }

      if (measurementTransactions.unconfirmedCount == 0) {
        return markTermination("DRAIN_ALL_CONFIRMED", true)
      }

      val latestDeadline = measurementTransactions.latestSubmittedAtMs + transactionDrainMillis
      if (simulationClock.currentTime >= latestDeadline) {
        return markTermination("DRAIN_DEADLINE_REACHED", true)
      }

      // Long inter-block gaps are expected for large BCI values. Once the
      // measurement window has completed, generic inactivity must not truncate
      // transaction follow-up before the cohort has resolved or reached its
      // prespecified drain deadline.
      return false
    }

    if (
      canonicalProgressStallMillis > 0L &&
      (phase == Phase.WARMUP || phase == Phase.MEASUREMENT)
    ) {
      val noProgressForMs = simulationClock.currentTime - canonicalProgressLastAdvanceTimeMs
      if (noProgressForMs >= canonicalProgressStallMillis) {
        System.err.println(
          "[3SIM-canonical-stall] phase=${phase.name} " +
            "canonicalHighWater=$canonicalProgressHighWater " +
            "canonicalNow=${canonicalBlocksObservedForWindowControl()} " +
            "noProgressForMs=$noProgressForMs " +
            "stallThresholdMs=$canonicalProgressStallMillis " +
            "proposals=$totalBlockProposalsAllPhases " +
            "longestChain=${maxBlockchainLengthCondition.currentLength} " +
            "submittedTxAllPhases=$totalTransactionSubmissionsAllPhases " +
            "timeMs=${simulationClock.currentTime}"
        )
        return markTermination("CANONICAL_PROGRESS_STALL", false)
      }
    }

    if (inactivityThresholdCondition.hasProlongedInactivityExceeded()) {
      return markTermination("INACTIVITY", false)
    }
    return false
  }

  private fun reportWorkloadLimit(kind: String, limit: Long) {
    System.err.println(
      "[3SIM-workload-limit] kind=$kind limit=$limit phase=${phase.name} " +
        "canonical=${canonicalBlocksObservedForWindowControl()} " +
        "proposals=$totalBlockProposalsAllPhases " +
        "submittedTxAllPhases=$totalTransactionSubmissionsAllPhases " +
        "longestChain=${maxBlockchainLengthCondition.currentLength} " +
        "timeMs=${simulationClock.currentTime}"
    )
  }

  private fun markTermination(reason: String, followUpCompleted: Boolean): Boolean {
    if (terminationReason == "NOT_TERMINATED") {
      terminationReason = reason
      transactionFollowUpCompleted = followUpCompleted
    }
    return true
  }

  fun setScalabilityStateTracker(scalabilityStateTracker: ScalabilityStateTracker) {
    this.scalabilityStateTracker = scalabilityStateTracker
  }

  fun setSimulationClock(simulationClock: SimulationClock) {
    this.simulationClock = simulationClock
    this.inactivityThresholdCondition.simulationClock = simulationClock
    if (refinedWindowEnabled) {
      canonicalProgressLastAdvanceTimeMs = simulationClock.currentTime
    }
  }

  private fun monitorThroughputForNewlyConfirmedBlock(confirmedBlock: Block, occurrenceTime: Long) {
    val observationTime = occurrenceTime - lastThroughputCheckTimestamp
    if (observationTime <= 0L) return

    val throughput = TransactionThroughputCalculator(
      numberOfConfirmedTransactions = confirmedBlock.transactions.size,
      observationTime = observationTime
    ).calculate().value
    val confirmationLatency = occurrenceTime - confirmedBlock.blockMinedTimestamp

    if (failureLog.isFailureOngoing()) {
      if (throughput > failureThroughputThreshold) failureLog.failureEnded(occurrenceTime)
    } else if (throughput <= failureThroughputThreshold) {
      failureLog.failureStarted(occurrenceTime)
    }

    if (failureLog.isFailureOngoing()) {
      throughputsDuringFailure.add(throughput)
      confirmationLatenciesDuringFailure.add(confirmationLatency.toDouble())
    } else {
      throughputsWithoutFailure.add(throughput)
      confirmationLatenciesWithoutFailure.add(confirmationLatency.toDouble())
    }
    lastThroughputCheckTimestamp = occurrenceTime
  }

  private fun isCanonicalType(blockType: BlockType): Boolean =
    blockType == BlockType.IncludedBlock || blockType == BlockType.ConfirmedBlock

  private fun calculateMajorityThreshold(): Int = (nodes.size / 2) + 1
  private fun calculateNumberOfConfirmedBlocks(): Int = confirmedBlocks.getNumberOfValidBlocks()
  private fun calculateNumberOfCanonicalBlocks(): Int = canonicalMeasurementBlocks.getNumberOfValidBlocks()
  private fun calculateNumberOfConfirmedTransactions(): Int = confirmedBlocks.getValidBlocks().sumOf { it.first.transactions.size }
  private fun calculateNumberOfStaleBlocks(): Int = staleBlocks.getNumberOfValidBlocks()
  private fun calculateBlocksProposedPerNode(): Collection<Int> = blocksProposedPerNode.values
  private fun calculateCanonicalBlocksPerNode(): Collection<Int> {
    val counts = nodes.associate { it.id to 0 }.toMutableMap()
    canonicalMeasurementBlocks.getValidBlocks().forEach { (block, _) ->
      block.originId?.let { origin -> counts[origin] = (counts[origin] ?: 0) + 1 }
    }
    return nodes.sortedBy { it.id }.map { counts[it.id] ?: 0 }
  }
  private fun calculateHashPowerPerNode(): Collection<Double> = nodes.map { it.resourcePower }
  private fun calculateNumberOfNodesPerRegion(): Collection<Int> = nodes.groupingBy { it.geographicalRegion.region }.eachCount().values
  private fun calculateBlockProposalTimeAndConfirmationTimePerConfirmedBlock(): Collection<Pair<Long, Long>> =
    confirmedBlocks.getValidBlocks().map { Pair(it.first.blockMinedTimestamp, it.second) }

  private fun calculateTokensHeldPerNode(): List<Double> {
    val reward = blockReward ?: throw IllegalStateException("Block reward is not set")
    val blocks = confirmedBlocks.getValidBlocks().filter { it.first.originId != null }.groupBy { it.first.originId }
    return nodes.map { node ->
      blocks[node.id]?.fold(0.0) { acc, block ->
        acc + reward + block.first.transactions.sumOf { it.fee }
      } ?: 0.0
    }
  }

  private fun calculateMeanTimeBetweenFailures(observationTime: Long): Double {
    val numFailures = failureLog.getNumberOfFailures()
    if (numFailures <= 0) return -1.0
    return observationTime.toDouble() / numFailures
  }

  private fun calculateMeanTimeToRepair(): Double = failureLog.calculateMeanFailureDuration()
  private fun calculateNumberOfGeographicalRegions(): Int = geographicalRegions.getNumberOfRegions()
  private fun calculateAverageThroughputDuringFailure(): Double = throughputsDuringFailure.averageOr(-1.0)
  private fun calculateAverageConfirmationLatencyDuringFailure(): Double = confirmationLatenciesDuringFailure.averageOr(-1.0)
  private fun calculateAverageThroughputWithoutFailure(): Double = throughputsWithoutFailure.averageOr(-1.0)
  private fun calculateAverageConfirmationLatencyWithoutFailure(): Double = confirmationLatenciesWithoutFailure.averageOr(-1.0)
  private companion object {
    const val UNSEEN_RECEIPT_TIME: Long = Long.MIN_VALUE
  }

}

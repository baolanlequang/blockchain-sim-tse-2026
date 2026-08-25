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
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegions
import org.palladiosimulator.blockchainsystems.core.mining.BlockMinedTraceEvent
import org.palladiosimulator.blockchainsystems.core.monitoring.abstractions.SimulationMonitor
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
) : SimulationMonitor {

  private enum class Phase { LEGACY, WARMUP, MEASUREMENT, DRAIN }

  private lateinit var nodes: MutableSet<BlockchainSystemNode>
  private lateinit var geographicalRegions: GeographicalRegions
  private lateinit var transactionSubmissionProcess: TransactionSubmissionProcess
  private lateinit var simulationClock: SimulationClock

  private val refinedWindowEnabled: Boolean get() = measuredBlocksPerValidator > 0
  private var phase: Phase = Phase.LEGACY
  private var warmupTargetCanonicalBlocks = 0
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

  /** Tracks majority-visible canonical-chain membership (Included or Confirmed) for window control. */
  private lateinit var canonicalProgressBlocks: BlocksMap
  /** Tracks blocks that reach the configured confirmation depth; used for transaction follow-up. */
  private lateinit var confirmationProgressBlocks: BlocksMap
  /** Canonical-chain blocks observed only inside the measurement window (Included or Confirmed). */
  private lateinit var canonicalMeasurementBlocks: BlocksMap

  private var numberOfSubmittedTransactions: Int = 0
  private var totalBlockProposalsAllPhases: Int = 0
  private var totalTransactionSubmissionsAllPhases: Int = 0
  private var blockReward: Double? = null
  private lateinit var blocksProposedPerNode: CounterMap<String>
  private lateinit var includedBlocks: BlocksMap
  private lateinit var confirmedBlocks: BlocksMap
  private lateinit var staleBlocks: BlocksMap
  private lateinit var forkedBlocks: BlocksMap

  /** txId -> submission time for transactions submitted in the measurement window. */
  private val measurementTransactions = linkedMapOf<String, Long>()
  /** txId -> first qualifying canonical confirmation time. */
  private val measurementTransactionConfirmationTimes = mutableMapOf<String, Long>()

  private val failureLog = BlockchainSystemFailureLog()
  private val throughputsDuringFailure: MutableList<Double> = mutableListOf()
  private val confirmationLatenciesDuringFailure: MutableList<Long> = mutableListOf()
  private val throughputsWithoutFailure: MutableList<Double> = mutableListOf()
  private val confirmationLatenciesWithoutFailure: MutableList<Long> = mutableListOf()
  private var lastThroughputCheckTimestamp: Long = 0

  /** Runtime attack-round tracker for the revised paper-defined SPSM. */
  private lateinit var selfishMiningAttackRoundTracker: SelfishMiningAttackRoundTracker

  override fun initialize(blockchainSystem: BlockchainSystem) {
    require(warmupBlocksPerValidator >= 0) { "warmupBlocksPerValidator must be >= 0" }
    require(measuredBlocksPerValidator >= 0) { "measuredBlocksPerValidator must be >= 0" }
    require(transactionDrainMillis >= 0L) { "transactionDrainMillis must be >= 0" }

    nodes = blockchainSystem.nodes
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
    includedBlocks = BlocksMap(majorityThreshold)
    confirmedBlocks = BlocksMap(majorityThreshold)
    staleBlocks = BlocksMap(majorityThreshold)
    forkedBlocks = BlocksMap(majorityThreshold)
    canonicalProgressBlocks = BlocksMap(majorityThreshold)
    confirmationProgressBlocks = BlocksMap(majorityThreshold)
    canonicalMeasurementBlocks = BlocksMap(majorityThreshold)

    if (refinedWindowEnabled) {
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
    val effectiveEnd = if (measurementEndTimeMs > 0L) measurementEndTimeMs else finalSystemTime
    val measurementDuration = if (refinedWindowEnabled) {
      (effectiveEnd - measurementStartTimeMs).coerceAtLeast(0L)
    } else finalSystemTime
    val spsmSummary = selfishMiningAttackRoundTracker.summary()
    val followUpSummary = buildTransactionFollowUpSummary(finalSystemTime)

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
      canonicalBlocksObservedForWindowControl = canonicalProgressBlocks.getNumberOfValidBlocks(),
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
      blockRateObservationTimeMs = finalSystemTime,
      transactionRateObservationTimeMs = if (measurementEndTimeMs > 0L) measurementEndTimeMs else finalSystemTime,
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
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          if (BlockUtils.isBlockForked(e.block)) {
            forkedBlocks.addNodeToBlock(e.block, logOrigin.id, e.occurrenceTime)
          }
          blocksProposedPerNode.increment(logOrigin.id)
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
        val confirmationBecameValid = if (e.appendedBlockType == BlockType.ConfirmedBlock) {
          confirmationProgressBlocks.addNodeToBlock(e.appendedBlock, logOrigin.id, e.occurrenceTime)
        } else false

        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          val measurementBecameValid = addMeasurementBlock(
            e.appendedBlockType, e.appendedBlock, logOrigin.id, e.occurrenceTime)
          if (phase == Phase.MEASUREMENT && isCanonicalType(e.appendedBlockType)) {
            canonicalMeasurementBlocks.addNodeToBlock(e.appendedBlock, logOrigin.id, e.occurrenceTime)
          }
          if (measurementBecameValid && e.appendedBlockType == BlockType.ConfirmedBlock) {
            monitorThroughputForNewlyConfirmedBlock(e.appendedBlock, e.occurrenceTime)
          }
        }

        if (confirmationBecameValid) {
          recordMeasurementTransactionConfirmations(e.appendedBlock, e.occurrenceTime)
        }
        if (canonicalBecameValid) {
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
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          removeMeasurementBlock(e.oldBlockType, e.block.hash, nodeId)
          val measurementBecameValid = addMeasurementBlock(e.newBlockType, e.block, nodeId, e.occurrenceTime)

          if (phase == Phase.MEASUREMENT) {
            if (oldCanonical && !newCanonical) {
              canonicalMeasurementBlocks.removeNodeFromBlock(e.block.hash, nodeId)
            } else if (!oldCanonical && newCanonical) {
              canonicalMeasurementBlocks.addNodeToBlock(e.block, nodeId, e.occurrenceTime)
            }
          }

          if (measurementBecameValid && e.newBlockType == BlockType.ConfirmedBlock) {
            monitorThroughputForNewlyConfirmedBlock(e.block, e.occurrenceTime)
          }
        }

        if (confirmationBecameValid) {
          recordMeasurementTransactionConfirmations(e.block, e.occurrenceTime)
        }
        if (canonicalBecameValid) {
          updateRefinedPhaseAfterCanonicalProgress(e.occurrenceTime)
        }
      }

      TransactionSubmittedTraceEvent.EVENT_TYPE -> {
        val e = event as TransactionSubmittedTraceEvent
        totalTransactionSubmissionsAllPhases++
        if (phase == Phase.LEGACY || phase == Phase.MEASUREMENT) {
          numberOfSubmittedTransactions++
          if (phase == Phase.MEASUREMENT) {
            measurementTransactions.putIfAbsent(e.transaction.txId, e.transaction.creationTime)
          }
        }
      }
    }
  }

  private fun updateRefinedPhaseAfterCanonicalProgress(occurrenceTime: Long) {
    if (!refinedWindowEnabled) return
    val canonicalCount = canonicalProgressBlocks.getNumberOfValidBlocks()

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

    if (phase == Phase.MEASUREMENT && canonicalCount >= measurementTargetCanonicalBlocks) {
      measurementEndTimeMs = occurrenceTime
      phase = Phase.DRAIN
      transactionSubmissionProcess.stopTransactionSubmissionProcess()
    }
  }

  private fun beginMeasurement(occurrenceTime: Long) {
    phase = Phase.MEASUREMENT
    measurementStartTimeMs = occurrenceTime
    numberOfSubmittedTransactions = 0
    blocksProposedPerNode = CounterMap.create(nodes.map { it.id })
    includedBlocks.clear()
    confirmedBlocks.clear()
    canonicalMeasurementBlocks.clear()
    staleBlocks.clear()
    forkedBlocks.clear()
    measurementTransactions.clear()
    measurementTransactionConfirmationTimes.clear()
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
      val submittedAt = measurementTransactions[transaction.txId] ?: continue
      val deadline = submittedAt + transactionDrainMillis
      if (occurrenceTime <= deadline) {
        measurementTransactionConfirmationTimes.putIfAbsent(transaction.txId, occurrenceTime)
      }
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

  private data class FollowUpAtTime(
    var events: Int = 0,
    var censored: Int = 0
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
     * Group event/censor counts by follow-up time for the Kaplan-Meier RMCL.
     * This is much smaller than materializing one serializable object per
     * transaction, especially when many transactions share the T_drain censoring
     * time. The calculation still includes EVERY measurement transaction.
     */
    val grouped = sortedMapOf<Long, FollowUpAtTime>()
    val retained = if (retainTransactionFollowUpObservations) {
      ArrayList<TransactionFollowUpObservation>(measurementTransactions.size)
    } else {
      null
    }

    var confirmedCount = 0

    measurementTransactions.forEach { (txId, submittedAt) ->
      val confirmation = measurementTransactionConfirmationTimes[txId]
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

      val group = grouped.getOrPut(followUp) { FollowUpAtTime() }
      if (confirmed) group.events++ else group.censored++

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

    val total = measurementTransactions.size
    val censoredCount = total - confirmedCount
    val ratio = confirmedCount.toDouble() / total.toDouble()
    val rmcl = restrictedMeanFromGroupedFollowUp(grouped, total, transactionDrainMillis)

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
   * `grouped` contains the complete follow-up population, so this result is
   * numerically equivalent to the former per-observation implementation.
   */
  private fun restrictedMeanFromGroupedFollowUp(
    grouped: Map<Long, FollowUpAtTime>,
    populationSize: Int,
    tau: Long
  ): Double? {
    if (populationSize <= 0 || tau <= 0L) return null

    var atRisk = populationSize
    var survival = 1.0
    var previousTime = 0L
    var area = 0.0

    for ((timeRaw, group) in grouped) {
      val time = timeRaw.coerceIn(0L, tau)
      if (time > previousTime) {
        area += survival * (time - previousTime).toDouble()
      }
      if (atRisk > 0 && group.events > 0) {
        survival *= 1.0 - group.events.toDouble() / atRisk.toDouble()
      }
      atRisk -= group.events + group.censored
      previousTime = time
      if (time >= tau) break
    }

    if (previousTime < tau) {
      area += survival * (tau - previousTime).toDouble()
    }
    return area
  }

  private fun isSpsmObservationPhase(): Boolean = phase == Phase.LEGACY || phase == Phase.MEASUREMENT

  override fun shouldTerminate(): Boolean {
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

      val allConfirmed = measurementTransactions.keys.all { it in measurementTransactionConfirmationTimes }
      if (allConfirmed) {
        return markTermination("DRAIN_ALL_CONFIRMED", true)
      }

      val latestDeadline = measurementTransactions.values.maxOrNull()!! + transactionDrainMillis
      if (simulationClock.currentTime >= latestDeadline) {
        return markTermination("DRAIN_DEADLINE_REACHED", true)
      }
    }

    if (inactivityThresholdCondition.hasProlongedInactivityExceeded()) {
      return markTermination("INACTIVITY", false)
    }
    return false
  }

  private fun markTermination(reason: String, followUpCompleted: Boolean): Boolean {
    if (terminationReason == "NOT_TERMINATED") {
      terminationReason = reason
      transactionFollowUpCompleted = followUpCompleted
    }
    return true
  }

  fun setSimulationClock(simulationClock: SimulationClock) {
    this.simulationClock = simulationClock
    this.inactivityThresholdCondition.simulationClock = simulationClock
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
      confirmationLatenciesDuringFailure.add(confirmationLatency)
    } else {
      throughputsWithoutFailure.add(throughput)
      confirmationLatenciesWithoutFailure.add(confirmationLatency)
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
  private fun calculateAverageThroughputDuringFailure(): Double = if (throughputsDuringFailure.isEmpty()) -1.0 else throughputsDuringFailure.average()
  private fun calculateAverageConfirmationLatencyDuringFailure(): Double = if (confirmationLatenciesDuringFailure.isEmpty()) -1.0 else confirmationLatenciesDuringFailure.average()
  private fun calculateAverageThroughputWithoutFailure(): Double = if (throughputsWithoutFailure.isEmpty()) -1.0 else throughputsWithoutFailure.average()
  private fun calculateAverageConfirmationLatencyWithoutFailure(): Double = if (confirmationLatenciesWithoutFailure.isEmpty()) -1.0 else confirmationLatenciesWithoutFailure.average()
}

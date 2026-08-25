package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedCreationAudit
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.*
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet
import org.palladiosimulator.blockchainsystems.threesim.monitoring.ThreesimSimulationMonitor
import org.palladiosimulator.blockchainsystems.threesim.monitoring.ThreesimSimulationMonitorState
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters
import kotlin.math.ln

/** Factory for the legacy metrics plus the refined-study execution audit. */
class ThreesimSimulationRoundResultFactory(
  private val threesimSimulationParameters: ThreesimSimulationParameters,
  private val monitor: ThreesimSimulationMonitor,
  private val finalSystemTime: Long,
  private val creationAudit: RefinedCreationAudit? = null
) {
  fun createSimulationRoundResult(): ThreesimSimulationRoundResult {
    val state = monitor.getFinalState(finalSystemTime)

    val legacyObservationTime = if (state.refinedWindowEnabled && state.measurementDurationMs > 0L) {
      state.measurementDurationMs
    } else {
      finalSystemTime
    }

    val outputMetrics = OutputMetricsSet.from(
      ShannonEntropyCalculator(
        k = threesimSimulationParameters.shannonEntropyK,
        blocksProposedPerNode = state.blocksProposedPerNode
      ).calculate(),
      GeographicalDiversityCalculator(
        numberOfNodes = state.numberOfNodes,
        numberOfRegions = state.numberOfGeographicalRegions,
        numberOfNodesPerRegion = state.numberOfNodesPerRegion
      ).calculate(),
      NakamotoCoefficientCalculator(
        state.hashPowerPerNode,
        threshold = threesimSimulationParameters.nakamotoCoefficientThreshold
      ).calculate(),
      HerfindahlHirschmanIndexCalculator(
        tokensHeldPerNode = state.tokensHeldPerNode
      ).calculate(),
      GiniCoefficientCalculator(
        tokensHeldPerNode = state.tokensHeldPerNode
      ).calculate(),
      AvailabilityScalabilityCalculator(
        numberOfConfirmedTransactions = state.numberOfConfirmedTransactions,
        numberOfTransactions = state.numberOfSubmittedTransactions
      ).calculate(),
      TransactionThroughputCalculator(
        numberOfConfirmedTransactions = state.numberOfConfirmedTransactions,
        observationTime = legacyObservationTime
      ).calculate(),
      AvailabilitySecurityCalculator(
        meanTimeBetweenFailures = state.meanTimeBetweenFailures,
        meanTimeToRepair = state.meanTimeToRepair
      ).calculate(),
      ConsistencyCalculator(
        blockProposalTimeAndConfirmationTimePerConfirmedBlock = state.blockProposalTimeAndConfirmationTimePerConfirmedBlock
      ).calculate(),
      FaultToleranceCalculator(
        averageThroughputWithoutFailures = state.averageThroughputDuringNormalOperation,
        averageThroughputWithFailures = state.averageThroughputDuringFailure,
        averageConfirmationLatencyWithoutFailures = state.averageConfirmationLatencyDuringNormalOperation,
        averageConfirmationLatencyWithFailures = state.averageConfirmationLatencyDuringFailure
      ).calculate(),
      ReliabilityCalculator(
        timespan = threesimSimulationParameters.reliabilityObservationTimespan,
        meanTimeBetweenFailures = state.meanTimeBetweenFailures
      ).calculate(),
      StaleBlockRateCalculator(
        numberOfStaleBlocks = state.numberOfStaleBlocks,
        numberOfConfirmedBlocks = state.numberOfConfirmedBlocks
      ).calculate()
    )

    return ThreesimSimulationRoundResult(
      outputMetrics = outputMetrics,
      refinedExecutionAudit = createRefinedAudit(state)
    )
  }

  private fun createRefinedAudit(state: ThreesimSimulationMonitorState): RefinedExecutionAudit {
    /*
     * Follow-up metrics are computed by the monitor from the complete transaction
     * population. The optional raw list may intentionally be empty in production.
     * Never recompute TCR/censoring from the retained list.
     */
    return RefinedExecutionAudit(
      creation = creationAudit,
      refinedWindowEnabled = state.refinedWindowEnabled,
      warmupBlocksPerValidator = threesimSimulationParameters.warmupBlocksPerValidator,
      measuredBlocksPerValidator = threesimSimulationParameters.measuredBlocksPerValidator,
      transactionDrainMillis = threesimSimulationParameters.transactionDrainMillis,
      warmupTargetCanonicalBlocks = state.warmupTargetCanonicalBlocks,
      measurementTargetCanonicalBlocks = state.measurementTargetCanonicalBlocks,
      canonicalBlocksObservedForWindowControl = state.canonicalBlocksObservedForWindowControl,
      measurementStartTimeMs = state.measurementStartTimeMs,
      measurementEndTimeMs = state.measurementEndTimeMs,
      measurementDurationMs = state.measurementDurationMs,
      executionPhaseAtTermination = state.executionPhaseAtTermination,
      terminationReason = state.terminationReason,
      measurementWindowCompleted = !state.refinedWindowEnabled || state.measurementEndTimeMs > 0L,
      transactionFollowUpCompleted = state.transactionFollowUpCompleted,
      totalBlockProposalsAllPhases = state.totalBlockProposalsAllPhases,
      totalTransactionSubmissionsAllPhases = state.totalTransactionSubmissionsAllPhases,
      blockRateObservationTimeMs = state.blockRateObservationTimeMs,
      transactionRateObservationTimeMs = state.transactionRateObservationTimeMs,
      measurementSubmittedTransactions = state.numberOfSubmittedTransactions,
      measurementConfirmedTransactionsInCanonicalBlocks = state.numberOfConfirmedTransactions,
      measurementCanonicalBlocks = state.numberOfCanonicalBlocks,
      measurementConfirmedBlocks = state.numberOfConfirmedBlocks,
      measurementStaleBlocks = state.numberOfStaleBlocks,
      measurementTransactionsConfirmedWithinDrain = state.measurementTransactionsConfirmedWithinDrain,
      measurementTransactionsRightCensored = state.measurementTransactionsRightCensored,
      transactionFollowUpObservationCount = state.transactionFollowUpObservationCount,
      transactionFollowUpObservationsRetained = state.transactionFollowUpObservationsRetained,
      transactionFollowUpObservations = state.transactionFollowUpObservations,
      proposalEntropyNormalized = normalizedEntropy(state.blocksProposedPerNode),
      canonicalBlockHhiNormalized = normalizedHhi(state.canonicalBlocksPerNode),
      transactionsPerSecond = if (state.measurementDurationMs > 0L) {
        state.numberOfConfirmedTransactions.toDouble() / (state.measurementDurationMs.toDouble() / 1000.0)
      } else 0.0,
      transactionConfirmationRatio = state.transactionConfirmationRatio,
      // IMPORTANT MANUSCRIPT CONSISTENCY NOTE:
      // The current draft contains two SBR wordings: Section V-D/Table I says
      // stale/canonical, whereas Appendix Eq. (33) defines N_stale/N_produced.
      // This implementation follows the formal Appendix equation, with
      // N_produced = N_canonical + N_stale for the frozen measurement window.
      // Raw canonical/stale counts are serialized as well, so the alternative
      // ratio remains auditable until the manuscript wording is reconciled.
      staleBlockRatio = run {
        val produced = state.numberOfCanonicalBlocks + state.numberOfStaleBlocks
        if (produced > 0) state.numberOfStaleBlocks.toDouble() / produced.toDouble() else 0.0
      },
      restrictedMeanConfirmationLatencyMs = state.restrictedMeanConfirmationLatencyMs,
      // Paper-defined SPSM is calculated in the runtime from explicit selfish-mining
      // attack rounds. Pair-level analysis pools these counts across R_S and R_E; it
      // must not average the per-run percentages.
      selfishMiningAttackRoundsStarted = state.selfishMiningAttackRoundsStarted,
      successfulSelfishMiningAttackRounds = state.successfulSelfishMiningAttackRounds,
      failedSelfishMiningAttackRounds = state.failedSelfishMiningAttackRounds,
      ambiguousSelfishMiningAttackRounds = state.ambiguousSelfishMiningAttackRounds,
      unambiguousSelfishMiningAttackRounds = state.unambiguousSelfishMiningAttackRounds,
      selfishMiningSuccessProbability = state.selfishMiningSuccessProbability
    )
  }

  private fun normalizedEntropy(counts: Collection<Int>): Double {
    val n = counts.size
    val total = counts.sum()
    if (n < 2 || total <= 0) return 0.0
    var entropy = 0.0
    for (count in counts) {
      if (count <= 0) continue
      val p = count.toDouble() / total.toDouble()
      entropy -= p * ln(p)
    }
    return entropy / ln(n.toDouble())
  }

  private fun normalizedHhi(counts: Collection<Int>): Double {
    val n = counts.size
    val total = counts.sum()
    if (n < 2 || total <= 0) return 0.0
    val raw = counts.sumOf {
      val p = it.toDouble() / total.toDouble()
      p * p
    }
    val invN = 1.0 / n.toDouble()
    return (raw - invN) / (1.0 - invN)
  }

}

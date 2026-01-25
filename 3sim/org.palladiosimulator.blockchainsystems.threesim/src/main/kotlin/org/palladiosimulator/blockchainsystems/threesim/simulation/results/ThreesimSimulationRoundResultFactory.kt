package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.*
import org.palladiosimulator.blockchainsystems.threesim.monitoring.ThreesimSimulationMonitor
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters

/**
 * Factory for creating a [ThreesimSimulationRoundResult] based on the final state of the simulation.
 *
 * @author Davis Riedel
 */
class ThreesimSimulationRoundResultFactory(
  private val threesimSimulationParameters: ThreesimSimulationParameters,
  private val monitor: ThreesimSimulationMonitor,
  private val finalSystemTime: Long
) {
  fun createSimulationRoundResult(): ThreesimSimulationRoundResult {

    val state = monitor.getFinalState(finalSystemTime)

    return ThreesimSimulationRoundResult(
      outputMetrics = OutputMetricsSet.from(

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
          observationTime = finalSystemTime
        ).calculate(),

        AvailabilitySecurityCalculator(
          meanTimeBetweenFailures = state.meanTimeBetweenFailures,
          meanTimeToRepair = state.meanTimeToRepair
        ).calculate(),

        ConsistencyCalculator(
          blockProposalTimeAndConfirmationTimePerConfirmedBlock = state.blockProposalTimeAndConfirmationTimePerConfirmedBlock
        ).calculate(),

        ConfirmationLatencyCalculator(
		     state.blockProposalTimeAndConfirmationTimePerConfirmedBlock
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
    )
  }
}

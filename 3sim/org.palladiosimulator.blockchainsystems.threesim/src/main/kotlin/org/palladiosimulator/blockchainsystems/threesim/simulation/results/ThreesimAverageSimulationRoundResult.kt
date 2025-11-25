package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.AvailabilityScalability
import org.palladiosimulator.blockchainsystems.threesim.metrics.AvailabilitySecurity
import org.palladiosimulator.blockchainsystems.threesim.metrics.TransactionThroughput
import org.palladiosimulator.blockchainsystems.threesim.metrics.Consistency
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultTolerance
import org.palladiosimulator.blockchainsystems.threesim.metrics.GeographicalDiversity
import org.palladiosimulator.blockchainsystems.threesim.metrics.GiniCoefficient
import org.palladiosimulator.blockchainsystems.threesim.metrics.HerfindahlHirschmanIndex
import org.palladiosimulator.blockchainsystems.threesim.metrics.NakamotoCoefficient
import org.palladiosimulator.blockchainsystems.threesim.metrics.Reliability
import org.palladiosimulator.blockchainsystems.threesim.metrics.ShannonEntropy
import org.palladiosimulator.blockchainsystems.threesim.metrics.StaleBlockRate
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.AvailabilityScalabilityCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.AvailabilitySecurityCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.TransactionThroughputCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.ConsistencyCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.FaultToleranceCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.GeographicalDiversityCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.GiniCoefficientCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.HerfindahlHirschmanIndexCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.NakamotoCoefficientCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.ReliabilityCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.ShannonEntropyCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.calculators.StaleBlockRateCalculator
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimAverageSimulationRoundResultSerializer

/**
 * Average result of several simulation rounds of 3SIM.
 *
 * @author Davis Riedel
 */
@Serializable(with = ThreesimAverageSimulationRoundResultSerializer::class)
class ThreesimAverageSimulationRoundResult private constructor(
  val results: List<AverageOutputMetric>
) {
  companion object {
    /**
     * Creates a set of average results from a list of simulation round results.
     *
     * @param simulationRoundResults the list of simulation round results
     * @return the set of average results
     */
    fun fromSimulationRoundResults(simulationRoundResults: List<ThreesimSimulationRoundResult>): ThreesimAverageSimulationRoundResult {
      return ThreesimAverageSimulationRoundResult(
        simulationRoundResults
          .flatMap { it.outputMetrics }
          .groupBy { it.name }
          .mapNotNull {
            when (it.key) {
              AvailabilityScalability.NAME -> AvailabilityScalabilityCalculator.calculateAverage(it.value as List<AvailabilityScalability>)
              AvailabilitySecurity.NAME -> AvailabilitySecurityCalculator.calculateAverage(it.value as List<AvailabilitySecurity>)
              Consistency.NAME -> ConsistencyCalculator.calculateAverage(it.value as List<Consistency>)
              FaultTolerance.NAME -> FaultToleranceCalculator.calculateAverage(it.value as List<FaultTolerance>)
              GeographicalDiversity.NAME -> GeographicalDiversityCalculator.calculateAverage(it.value as List<GeographicalDiversity>)
              GiniCoefficient.NAME -> GiniCoefficientCalculator.calculateAverage(it.value as List<GiniCoefficient>)
              HerfindahlHirschmanIndex.NAME -> HerfindahlHirschmanIndexCalculator.calculateAverage(it.value as List<HerfindahlHirschmanIndex>)
              NakamotoCoefficient.NAME -> NakamotoCoefficientCalculator.calculateAverage(it.value as List<NakamotoCoefficient>)
              Reliability.NAME -> ReliabilityCalculator.calculateAverage(it.value as List<Reliability>)
              ShannonEntropy.NAME -> ShannonEntropyCalculator.calculateAverage(it.value as List<ShannonEntropy>)
              StaleBlockRate.NAME -> StaleBlockRateCalculator.calculateAverage(it.value as List<StaleBlockRate>)
              TransactionThroughput.NAME -> TransactionThroughputCalculator.calculateAverage(it.value as List<TransactionThroughput>)
              else -> null
            }
          })
    }
  }
}
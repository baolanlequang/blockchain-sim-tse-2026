package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationRoundResult
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet

/** Result of a single simulation round of 3SIM. */
@Serializable
data class ThreesimSimulationRoundResult(
  val outputMetrics: OutputMetricsSet,
  /** Additional method-aligned audit/pilot output. Legacy consumers may ignore it. */
  val refinedExecutionAudit: RefinedExecutionAudit? = null
) : SimulationRoundResult

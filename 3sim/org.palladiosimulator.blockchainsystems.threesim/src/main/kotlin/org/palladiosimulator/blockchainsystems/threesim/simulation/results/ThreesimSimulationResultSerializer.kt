package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.json.Json
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResultSerializer

class ThreesimSimulationResultSerializer(
  private val jsonSerializer: Json,
) : SimulationResultSerializer {
  override fun serialize(result: SimulationResult): String {
    // This ensures the "type" field is not output
    return when (result) {
      is ThreesimMonteCarloSimulationResult -> jsonSerializer.encodeToString(result)
      is ThreesimSingleSimulationResult -> jsonSerializer.encodeToString(result)
      else -> throw IllegalArgumentException("Unsupported simulation result type: ${result::class.simpleName}")
    }
  }
}

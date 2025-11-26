package org.palladiosimulator.blockchainsystems.core.simulation.abstractions

interface SimulationResultSerializer {
  fun serialize(result: SimulationResult): String
}

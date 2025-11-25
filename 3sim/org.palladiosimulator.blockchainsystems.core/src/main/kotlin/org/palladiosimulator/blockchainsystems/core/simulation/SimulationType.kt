package org.palladiosimulator.blockchainsystems.core.simulation

import kotlinx.serialization.Serializable

@Serializable
enum class SimulationType {
  Single,
  MonteCarlo
}
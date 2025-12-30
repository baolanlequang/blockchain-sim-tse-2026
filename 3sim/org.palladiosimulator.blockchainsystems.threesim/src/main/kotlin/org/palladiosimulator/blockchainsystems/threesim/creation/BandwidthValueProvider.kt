package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAwareValueProvider


class BandwidthValueProvider(
  private val bandwidth: Long
) : SimulationLifecycleAwareValueProvider<Long> {
  override fun getValue(): Long? {
    return bandwidth
  }

  override fun initialize(simulationContext: SimulationContext) {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }

  override fun cleanup() {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }
}
package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAwareValueProvider

/**
 * A value provider for latency that returns a fixed value.
 *
 * @property latency The fixed latency value to be returned, in milliseconds (ms).
 *
 * @author Davis Riedel
 */
class StaticLatencyValueProvider(
  private val latency: Long
) : SimulationLifecycleAwareValueProvider<Long> {
  override fun getValue(): Long? {
    return latency
  }

  override fun initialize(simulationContext: SimulationContext) {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }

  override fun cleanup() {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }
}
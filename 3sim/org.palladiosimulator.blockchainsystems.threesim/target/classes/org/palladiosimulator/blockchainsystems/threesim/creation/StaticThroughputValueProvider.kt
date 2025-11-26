package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAwareValueProvider

/**
 * A static value provider for throughput that returns a fixed value.
 *
 * @property throughput The fixed throughput value to be returned, in Bit per second (bps).
 *
 * @author Davis Riedel
 */
class StaticThroughputValueProvider(
  private val throughput: Long
) : SimulationLifecycleAwareValueProvider<Long> {
  override fun getValue(): Long? {
    return throughput
  }

  override fun initialize(simulationContext: SimulationContext) {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }

  override fun cleanup() {
    // NOTE: No op, just for compatibility with the non-static latency value provider interface
  }
}
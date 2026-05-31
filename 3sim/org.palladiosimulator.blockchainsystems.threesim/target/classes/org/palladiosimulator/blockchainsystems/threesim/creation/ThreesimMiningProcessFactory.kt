package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.mining.MiningProcessImpl
import org.palladiosimulator.blockchainsystems.core.system.abstractions.MiningProcess
import org.palladiosimulator.blockchainsystems.core.system.abstractions.MiningProcessFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import java.util.random.RandomGenerator


/**
 * Factory for creating a [MiningProcess] for a blockchain node in 3SIM.
 *
 * @author Davis Riedel
 */
class ThreesimMiningProcessFactory(
  private val meanBlockTime: Double,
  private val resourcePowerCalculator: ResourcePowerCalculator
) : MiningProcessFactory {
  override fun createMiningProcess(nodeId: String): MiningProcess {
    // Node resource power in MH/s
    val nodeResourcePower = resourcePowerCalculator.getResourcePowerOfNode(nodeId)
      ?: throw IllegalArgumentException("Node with ID $nodeId does not have a defined resource power.")

    val nodeResourcePowerShare = nodeResourcePower / resourcePowerCalculator.calculateGlobalResourcePower()

    // meanBlockTime is specified in SECONDS, but the simulation clock runs in
    // MILLISECONDS (network latency and transmission durations are in ms). Convert to
    // ms here so block production and block propagation share the same time unit.
    // Without this, mined blocks land on the clock ~1000x too early, making propagation
    // appear far slower than block production -> pathological forking -> event explosion.
    val nodeAverageBlockArrivalTimeMs = (meanBlockTime * MILLIS_PER_SECOND) / nodeResourcePowerShare

    return MiningProcessImpl(
      nodeAverageBlockArrivalTimeMs,
      RandomGenerator.of("Random")
    )
  }

  companion object {
    private const val MILLIS_PER_SECOND = 1000.0
  }
}

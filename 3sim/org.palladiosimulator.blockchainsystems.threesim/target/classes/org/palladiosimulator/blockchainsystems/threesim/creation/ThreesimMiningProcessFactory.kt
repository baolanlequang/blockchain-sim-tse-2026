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

    val nodeAverageBlockArrivalTime = meanBlockTime / nodeResourcePowerShare

    return MiningProcessImpl(
      nodeAverageBlockArrivalTime,
      RandomGenerator.of("Random")
    )
  }
}

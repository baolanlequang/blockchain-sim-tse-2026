package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.mining.MiningProcessImpl
import org.palladiosimulator.blockchainsystems.core.system.abstractions.MiningProcess
import org.palladiosimulator.blockchainsystems.core.system.abstractions.MiningProcessFactory
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator

/** Creates node mining processes whose Poisson clocks use event-replication streams. */
class ThreesimMiningProcessFactory(
  private val meanBlockTime: Double,
  private val resourcePowerCalculator: ResourcePowerCalculator,
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : MiningProcessFactory {
  override fun createMiningProcess(nodeId: String): MiningProcess {
    val nodeResourcePower = resourcePowerCalculator.getResourcePowerOfNode(nodeId)
      ?: throw IllegalArgumentException("Node with ID $nodeId does not have a defined resource power.")

    val global = resourcePowerCalculator.calculateGlobalResourcePower()
    require(global > 0.0) { "Global resource power must be > 0." }
    val nodeResourcePowerShare = nodeResourcePower / global
    require(nodeResourcePowerShare > 0.0) { "Node $nodeId has non-positive hashing-power share." }

    // If meanBlockTime is BCI, the node rate is pi_i / BCI, hence its
    // exponential mean inter-block time is BCI / pi_i.
    val nodeAverageBlockArrivalTime = meanBlockTime / nodeResourcePowerShare

    return MiningProcessImpl(
      nodeAverageBlockArrivalTime,
      randomness.eventForNode("block-production", nodeId)
    )
  }
}

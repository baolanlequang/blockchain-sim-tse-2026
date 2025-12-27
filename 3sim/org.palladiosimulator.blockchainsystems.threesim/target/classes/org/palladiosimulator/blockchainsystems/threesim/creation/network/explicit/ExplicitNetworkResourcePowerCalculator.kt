package org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.Node
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator

/**
 * Calculates the global resource power (MH/s) of a blockchain system with an explicit network topology.
 * Allows to retrieve resource power of specific nodes in the network.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ExplicitNetworkResourcePowerCalculator(
  private val networkTopology: ExplicitNetworkTopology
) : ResourcePowerCalculator {
  private val resourcePowerPerNode: Map<String, Double> by lazy {
    networkTopology.nodes.associate { it.id to getResourcePowerOfNode(it) }
  }
  private val globalResourcePower: Double by lazy {
    resourcePowerPerNode.values.sum()
  }

  override fun calculateGlobalResourcePower(): Double {
    // Returns the pre-calculated global resource power
    return globalResourcePower // in MH/s
  }

  override fun getResourcePowerOfNode(nodeId: String): Double? {
    return resourcePowerPerNode[nodeId] // in MH/s
  }

  private fun getResourcePowerOfNode(node: Node): Double {
    return node.allocation.allocationContexts
      .filter { it.assemblyContext.encapsulatedComponent is MiningProcessComponent }
      .sumOf { it.resourceContainer.resourcePower }
  }
}
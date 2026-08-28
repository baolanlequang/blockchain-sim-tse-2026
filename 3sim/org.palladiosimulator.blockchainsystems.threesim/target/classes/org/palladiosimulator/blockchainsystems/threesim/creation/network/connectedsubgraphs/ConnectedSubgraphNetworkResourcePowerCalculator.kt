package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import org.palladiosimulator.blockchainsystems.threesim.creation.DirichletUtils
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness

/**
 * Allocates hashing power according to the paper's normalized concentration
 * parameter H_hash.  The sampled shares sum to one and therefore preserve the
 * aggregate block-production rate when node mining intervals are scaled from BCI.
 */
class ConnectedSubgraphNetworkResourcePowerCalculator(
  private val connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  private val nodeIdToNodeTemplateIdMapping: HashMap<String, String>,
  private val hashRateConcentration: Double?,
  private val nodeIdToIndexMapping: HashMap<String, Int>,
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : ResourcePowerCalculator {

  private val globalResourcePower: Double by lazy {
    connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences * getResourcePowerOfAllocation(it.allocation) }
      .let { if (it > 0.0) it else 1.0 }
  }

  private val hashShares: DoubleArray by lazy {
    val h = hashRateConcentration
      ?: throw IllegalArgumentException("Hashing-power concentration H_hash is missing.")
    val numberOfNodes = connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences }
    DirichletUtils.drawShares(
      numberOfNodes,
      h,
      randomness.network("hashing-power-allocation")
    )
  }

  val realizedHashingPowerH: Double
    get() = DirichletUtils.normalizedConcentration(hashShares)

  fun getHashingPowerShareOfNode(nodeId: String): Double? {
    val idx = nodeIdToIndexMapping[nodeId] ?: return null
    return hashShares.getOrNull(idx)
  }

  private fun getResourcePowerOfAllocation(nodeAllocation: NodeAllocation): Double {
    return nodeAllocation.allocationContexts
      .filter { it.assemblyContext.encapsulatedComponent is MiningProcessComponent }
      .sumOf { it.resourceContainer.resourcePower }
  }

  override fun calculateGlobalResourcePower(): Double = globalResourcePower

  override fun getResourcePowerOfNode(nodeId: String): Double? {
    val share = getHashingPowerShareOfNode(nodeId)
      ?: throw IllegalArgumentException("Node with ID $nodeId does not have a hashing-power share.")
    return share * globalResourcePower
  }
}

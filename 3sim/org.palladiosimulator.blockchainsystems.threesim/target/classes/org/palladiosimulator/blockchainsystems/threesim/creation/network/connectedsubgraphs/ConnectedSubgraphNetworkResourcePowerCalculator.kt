package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.core.system.abstractions.ResourcePowerCalculator
import kotlin.collections.get
import org.palladiosimulator.blockchainsystems.threesim.creation.DirichletUtils

/**
 * This class calculates the global resource power of a connected subgraph network topology.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ConnectedSubgraphNetworkResourcePowerCalculator(
  private val connectedSubgraphsTopology: ConnectedSubgraphsNetworkTopology,
  private val nodeIdToNodeTemplateIdMapping: HashMap<String, String>,
  private val hashRateConcentration: Double?,
  private val nodeIdToIndexMapping: HashMap<String, Int>
) : ResourcePowerCalculator {
  private val resourcePowerPerNodeTemplate: Map<String, Double> by lazy {
    connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .associateBy { it.id }
      .mapValues { getResourcePowerOfAllocation(it.value.allocation) }
  }

  private val globalResourcePower: Double by lazy {
    connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences * getResourcePowerOfAllocation(it.allocation) }
  }

  private val dirichletDistribution: DoubleArray by lazy {
    if (hashRateConcentration == null) {
      throw IllegalArgumentException("Hash rate concentration value is missing.")
    }
    val numberOfNodes = connectedSubgraphsTopology
      .subgraphs.flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences }
    val hStar = (hashRateConcentration - 1/numberOfNodes)/(1 - 1/numberOfNodes)
    val alpha = DirichletUtils.calibrateAlpha(hStar, numberOfNodes)
    val distributions = DirichletUtils.generateDirichlet(alpha, numberOfNodes)
    return@lazy distributions
  }

  private fun getResourcePowerOfAllocation(nodeAllocation: NodeAllocation): Double {
    return nodeAllocation
      .allocationContexts
      .filter { it.assemblyContext.encapsulatedComponent is MiningProcessComponent }
      .sumOf { it.resourceContainer.resourcePower }
  }

  override fun calculateGlobalResourcePower(): Double {
    return globalResourcePower // in MH/s
  }

  override fun getResourcePowerOfNode(nodeId: String): Double? {
//    val nodeTemplateId = nodeIdToNodeTemplateIdMapping[nodeId]

    val nodeIdx = nodeIdToIndexMapping.getOrDefault(nodeId, 0)
    val nodePropotion = dirichletDistribution.get(nodeIdx) ?:
      throw IllegalArgumentException("Node with ID $nodeId does not have a defined resource power.")
//    val resourcePower = nodePropotion * (resourcePowerPerNodeTemplate[nodeTemplateId] ?: 0.0) // in MH/s
    val resourcePower = nodePropotion * globalResourcePower
    return resourcePower
  }
}
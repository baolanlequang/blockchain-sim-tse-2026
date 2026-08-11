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
    // hashRateConcentration (CSV column `hashrate_concentration`) is already the paper's
    // normalized H* in [0,1], NOT a raw HHI: raw HHI can never be below 1/N (QM-AM inequality),
    // but the LHS CSV contains values below 1/N for small-N rows (e.g. config_id 58:
    // hashrate_concentration=0.0048 < 1/81=0.0123), which is only possible if this column is
    // already H*. Re-applying (x - 1/N)/(1 - 1/N) here -- the RAW-HHI-to-H* transform -- to an
    // already-normalized H* double-transforms it (and produces negative values at H*=0, which
    // is mathematically invalid for any HHI-derived quantity). Use it directly as H*.
    val hStar = hashRateConcentration
    val alpha = DirichletUtils.calibrateAlphaForHHI(hStar, numberOfNodes)
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
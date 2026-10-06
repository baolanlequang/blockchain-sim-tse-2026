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

  /** Single-attacker design: node indices holding the fixed adversarial share. */
  private var adversarialIndices: Set<Int> = emptySet()
  private var adversarialShare: Double? = null
  private var sharesMaterialized = false

  /**
   * Give the adversarial node(s) a fixed combined share alpha of the total
   * hashing power. The honest nodes keep their Dirichlet draw (same random
   * stream), rescaled to 1 - alpha; by the aggregation property of the
   * Dirichlet distribution their shares stay Dirichlet with the same alpha
   * parameter. Must be called before any share is read.
   */
  fun assignAdversarialHashingPowerShare(adversarialNodeIds: Set<String>, alpha: Double) {
    check(!sharesMaterialized) { "Hashing-power shares were already drawn." }
    require(alpha in 0.0..0.5) { "Adversarial hashing-power share must lie in [0, 0.5]; got $alpha" }
    adversarialIndices = adversarialNodeIds.map {
      nodeIdToIndexMapping[it] ?: throw IllegalArgumentException("Unknown adversarial node $it")
    }.toSet()
    adversarialShare = alpha
  }

  private val hashShares: DoubleArray by lazy {
    sharesMaterialized = true
    val h = hashRateConcentration
      ?: throw IllegalArgumentException("Hashing-power concentration H_hash is missing.")
    val numberOfNodes = connectedSubgraphsTopology.subgraphs
      .flatMap { it.nodeTemplates }
      .sumOf { it.numberOfNodeOccurences }
    val drawn = DirichletUtils.drawShares(
      numberOfNodes,
      h,
      randomness.network("hashing-power-allocation")
    )
    val alpha = adversarialShare
    if (alpha == null || adversarialIndices.isEmpty()) {
      drawn
    } else {
      require(adversarialIndices.size < numberOfNodes) { "At least one honest node is required." }
      val honestSum = drawn.indices.filter { it !in adversarialIndices }.sumOf { drawn[it] }
      val perAttacker = alpha / adversarialIndices.size
      DoubleArray(numberOfNodes) { i ->
        if (i in adversarialIndices) perAttacker
        else if (honestSum > 0.0) drawn[i] / honestSum * (1.0 - alpha)
        else (1.0 - alpha) / (numberOfNodes - adversarialIndices.size)
      }
    }
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

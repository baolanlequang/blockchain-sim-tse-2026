package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent
import org.palladiosimulator.blockchainsystems.core.block.BlockValidatorImpl
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidatorFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver

/** Block-validator factory with a separate event-replication RNG stream per node. */
class ThreesimBlockValidatorFactory(
  private val nodeAllocationResolver: NodeAllocationResolver,
  private val randomness: RefinedExperimentRandomness = RefinedExperimentRandomness(0L, 0L)
) : BlockValidatorFactory {
  override fun createBlockValidator(nodeId: String): BlockValidator {
    val component = nodeAllocationResolver
      .getNodeAllocation(nodeId)
      ?.allocationContexts
      ?.map { it.assemblyContext.encapsulatedComponent }
      ?.firstOrNull { it is BlockValidatorComponent }
      as? BlockValidatorComponent
      ?: throw IllegalArgumentException("No BlockValidatorComponent found for node with ID: $nodeId")

    val adapter = BlockValidationDurationProviderAdapter.create(
      component.validationDuration,
      randomness.eventForNode("block-validation", nodeId)
    )

    return BlockValidatorImpl(adapter, component.isCrashed)
  }
}

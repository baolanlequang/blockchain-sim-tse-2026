package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValidatorComponent
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidatorFactory
import org.palladiosimulator.blockchainsystems.core.block.BlockValidatorImpl
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver
import java.util.random.RandomGenerator

/**
 * Factory implementation for creating a [BlockValidator] based on the
 * [BlockValidatorComponent] in the node allocation.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class ThreesimBlockValidatorFactory(
  private val nodeAllocationResolver: NodeAllocationResolver
) : BlockValidatorFactory {
  override fun createBlockValidator(nodeId: String): BlockValidator {
    val component = nodeAllocationResolver
      .getNodeAllocation(nodeId)
      ?.allocationContexts
      ?.map { it.assemblyContext.encapsulatedComponent }
      ?.firstOrNull { it is BlockValidatorComponent }
      as? BlockValidatorComponent
      ?: throw IllegalArgumentException(
        "No BlockValidatorComponent found for node with ID: $nodeId"
      )

    val adapter = BlockValidationDurationProviderAdapter.create(
      component.validationDuration, // in ms
      RandomGenerator.of("Random")
    )

    return BlockValidatorImpl(adapter)
  }
}
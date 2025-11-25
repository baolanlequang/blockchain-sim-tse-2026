package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockValiationDurationSpecification
import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import org.palladiosimulator.blockchainsystems.core.utils.RandomValueProvider
import java.util.random.RandomGenerator

/**
 * Adapter for providing block validation durations based on a
 * [BlockValidationDurationSpecification] using a [RandomValueProvider].
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockValidationDurationProviderAdapter private constructor(
  private val randomValueProvider: RandomValueProvider<Long>
) : ValueProvider<Long> {
  override fun getValue(): Long {
    return randomValueProvider.value // in ms
  }

  companion object {
    fun create(
      validationDurationSpecification: BlockValiationDurationSpecification,
      randomGenerator: RandomGenerator
    ): BlockValidationDurationProviderAdapter {
      val valuesToProbabilitiesMapping = validationDurationSpecification.values
        .associate { it.duration to it.probability }

      val valueProvider = RandomValueProvider.create(
        valuesToProbabilitiesMapping,
        randomGenerator
      )

      return BlockValidationDurationProviderAdapter(valueProvider)
    }
  }
}
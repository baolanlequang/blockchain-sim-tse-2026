package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification
import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import org.palladiosimulator.blockchainsystems.core.network.LinkThroughput
import org.palladiosimulator.blockchainsystems.core.utils.RandomValueProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.TemporalValueProviderAdapter
import java.util.random.RandomGenerator

/**
 * Adapter for a [ValueProvider] that provides [LinkThroughput] values based on the [DynamicLinkThroughputSpecification] from the metamodel.
 *
 * @param randomValueProvider the underlying random value provider that provides the throughput values.
 *
 * @author Davis Riedel
 */
class ThroughputValueProviderAdapter(
  randomValueProvider: RandomValueProvider<LinkThroughput>,
) : TemporalValueProviderAdapter<Long, LinkThroughput>(randomValueProvider) {
  companion object {
    fun create(
      dynamicLinkThroughputSpecification: DynamicLinkThroughputSpecification,
      randomGenerator: RandomGenerator
    ): ThroughputValueProviderAdapter {
      val valuesToProbabilitiesMapping = dynamicLinkThroughputSpecification.values.associate {
        LinkThroughput(
          it.throughput, // in bps
          it.duration
        ) to it.probability
      }

      val valueProvider = RandomValueProvider.create(
        valuesToProbabilitiesMapping,
        randomGenerator
      )

      return ThroughputValueProviderAdapter(valueProvider)
    }
  }
}
package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification
import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import org.palladiosimulator.blockchainsystems.core.network.LinkLatency
import org.palladiosimulator.blockchainsystems.core.utils.RandomValueProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.TemporalValueProviderAdapter
import java.util.random.RandomGenerator

/**
 * Adapter for a [ValueProvider] that provides [LinkLatency] values based on the [DynamicLinkLatencySpecification] from the metamodel.
 *
 * @param randomValueProvider the underlying random value provider that provides the latency values.
 *
 * @author Davis Riedel
 */
class LatencyValueProviderAdapter(
  randomValueProvider: RandomValueProvider<LinkLatency>,
) : TemporalValueProviderAdapter<Long, LinkLatency>(randomValueProvider) {
  companion object {
    fun create(
      dynamicLinkLatencySpecification: DynamicLinkLatencySpecification,
      randomGenerator: RandomGenerator
    ): LatencyValueProviderAdapter {
      val valuesToProbabilitiesMapping = dynamicLinkLatencySpecification.values.associate {
        LinkLatency(it.latency, it.duration) to it.probability
      }

      val valueProvider = RandomValueProvider.create(
        valuesToProbabilitiesMapping,
        randomGenerator
      )

      return LatencyValueProviderAdapter(valueProvider)
    }
  }
}
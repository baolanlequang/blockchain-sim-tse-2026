package org.palladiosimulator.blockchainsystems.threesim.creation.network

import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkLatencySpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.DynamicLinkThroughputSpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkLatencySpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkThroughputSpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkLatencySpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.StaticLinkThroughputSpecification
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.BandwidthSpecification
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectivitySpecification
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAwareValueProvider
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.LatencyValueProviderAdapter
import org.palladiosimulator.blockchainsystems.threesim.creation.StaticLatencyValueProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.StaticThroughputValueProvider
import org.palladiosimulator.blockchainsystems.threesim.creation.ThroughputValueProviderAdapter
import org.palladiosimulator.blockchainsystems.threesim.creation.BandwidthValueProvider
import java.util.random.RandomGenerator

/**
 * Abstract factory for creating a P2P network in 3SIM. Stores common methods for explicit and connected subgraphs network factories.
 *
 * @author Davis Riedel
 */
abstract class AbstractThreesimP2PNetworkFactory() : P2PNetworkFactory {

  protected fun createLatencyValueProvider(
    latencySpecification: LinkLatencySpecification
  ): SimulationLifecycleAwareValueProvider<Long> {
    return when (latencySpecification) {
      is StaticLinkLatencySpecification -> {
        StaticLatencyValueProvider(latencySpecification.latency)
      }

      is DynamicLinkLatencySpecification -> {
        LatencyValueProviderAdapter.create(
          latencySpecification,
          RandomGenerator.of("Random")
        )
      }

      else -> {
        throw IllegalArgumentException(
          "Unsupported latency specification type: ${latencySpecification::class.java.name}"
        )
      }
    }
  }

  protected fun createThroughputValueProvider(
    throughputSpecification: LinkThroughputSpecification
  ): SimulationLifecycleAwareValueProvider<Long> {
    return when (throughputSpecification) {
      is StaticLinkThroughputSpecification -> {
        StaticThroughputValueProvider(throughputSpecification.throughput)
      }

      is DynamicLinkThroughputSpecification -> {
        ThroughputValueProviderAdapter.create(
          throughputSpecification,
          RandomGenerator.of("Random")
        )
      }

      else -> {
        throw IllegalArgumentException(
          "Unsupported throughput specification type: ${throughputSpecification::class.java.name}"
        )
      }
    }
  }

  protected fun createBandwidthValueProvider(
    bandwidthSpecification: BandwidthSpecification
  ): SimulationLifecycleAwareValueProvider<Double> {
    return when (bandwidthSpecification) {
      is BandwidthSpecification -> {
        BandwidthValueProvider(bandwidthSpecification.bandwidth)
      }

      else -> {
        throw IllegalArgumentException(
          "Unsupported bandwidth specification type: ${bandwidthSpecification::class.java.name}"
        )
      }
    }
  }

  protected fun createBandwidthValueProviderWithValue(
    bandwidth: Double
  ): SimulationLifecycleAwareValueProvider<Double> {
    return BandwidthValueProvider(bandwidth)
  }


}
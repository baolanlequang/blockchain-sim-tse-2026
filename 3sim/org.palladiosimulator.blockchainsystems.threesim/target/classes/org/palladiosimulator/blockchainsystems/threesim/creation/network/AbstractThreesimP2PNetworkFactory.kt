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
    latencySpecification: LinkLatencySpecification,
    randomGenerator: RandomGenerator = RandomGenerator.of("Random")
  ): SimulationLifecycleAwareValueProvider<Long> {
    return when (latencySpecification) {
      is StaticLinkLatencySpecification -> {
        StaticLatencyValueProvider(latencySpecification.latency)
      }

      is DynamicLinkLatencySpecification -> {
        LatencyValueProviderAdapter.create(
          latencySpecification,
          randomGenerator
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
    throughputSpecification: LinkThroughputSpecification,
    randomGenerator: RandomGenerator = RandomGenerator.of("Random")
  ): SimulationLifecycleAwareValueProvider<Long> {
    return when (throughputSpecification) {
      is StaticLinkThroughputSpecification -> {
        StaticThroughputValueProvider(throughputSpecification.throughput)
      }

      is DynamicLinkThroughputSpecification -> {
        ThroughputValueProviderAdapter.create(
          throughputSpecification,
          randomGenerator
        )
      }

      else -> {
        throw IllegalArgumentException(
          "Unsupported throughput specification type: ${throughputSpecification::class.java.name}"
        )
      }
    }
  }


  /**
   * Sample one fixed latency value for a structural/network instance.
   *
   * The refined TSE execution design keeps network characteristics fixed across
   * event replications R_E that share one network instance R_S.  Therefore a
   * dynamic latency specification from the reusable base model is interpreted as
   * a distribution from which one L_ij value is drawn per undirected connection
   * using a network-seed stream; it is not re-sampled over simulated time.
   */
  protected fun createNetworkFixedLatencyValueProvider(
    latencySpecification: LinkLatencySpecification,
    randomGenerator: RandomGenerator
  ): SimulationLifecycleAwareValueProvider<Long> {
    val latency = when (latencySpecification) {
      is StaticLinkLatencySpecification -> latencySpecification.latency
      is DynamicLinkLatencySpecification -> {
        require(latencySpecification.values.isNotEmpty()) {
          "Dynamic latency specification must contain at least one value."
        }
        val weighted = latencySpecification.values.map { value ->
          require(value.probability >= 0.0 && value.probability.isFinite()) {
            "Latency probabilities must be finite and non-negative."
          }
          value to value.probability
        }
        val total = weighted.sumOf { it.second }
        require(total > 0.0 && total.isFinite()) {
          "Latency probabilities must have a finite positive sum."
        }
        var draw = randomGenerator.nextDouble() * total
        var selected = weighted.last().first.latency
        for ((value, probability) in weighted) {
          draw -= probability
          if (draw <= 0.0) {
            selected = value.latency
            break
          }
        }
        selected
      }
      else -> throw IllegalArgumentException(
        "Unsupported latency specification type: ${latencySpecification::class.java.name}"
      )
    }
    require(latency >= 0L) { "Connection latency must be >= 0 ms, got $latency." }
    return StaticLatencyValueProvider(latency)
  }

  /**
   * Refined experiments model propagation with B_ij^eff and L_ij only.
   * The legacy throughput provider is therefore kept permanently available so
   * it acts only as the existing link-up/link-down gate and introduces no
   * unreported time-varying failure process.
   */
  protected fun createAlwaysAvailableThroughputValueProvider(): SimulationLifecycleAwareValueProvider<Long> =
    StaticThroughputValueProvider(Long.MAX_VALUE)

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
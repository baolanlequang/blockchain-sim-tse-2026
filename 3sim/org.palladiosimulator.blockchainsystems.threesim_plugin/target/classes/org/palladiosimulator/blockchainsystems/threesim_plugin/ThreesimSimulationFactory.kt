package org.palladiosimulator.blockchainsystems.threesim_plugin

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.debug.core.ILaunchConfiguration
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ExplicitNetworkTopology
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.Simulation
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters
import org.palladiosimulator.blockchainsystems.plugin.logging.LogOutputProviderImpl
import org.palladiosimulator.blockchainsystems.plugin.simulation.MonteCarloSimulationProgressMonitorAdapter
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters
import org.palladiosimulator.blockchainsystems.plugin.simulation.abstractions.SimulationFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.ThreesimBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs.ConnectedSubgraphNetworkBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit.ExplicitNetworkBlockchainSystemFactory
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimMonteCarloSimulation
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSingleSimulation

/**
 * Factory for creating instances of [Simulation] for the 3SIM blockchain simulator.
 *
 * @author Davis Riedel
 */
class ThreesimSimulationFactory(
  private val simulationParameters: SimulationParameters
) : SimulationFactory {
  override fun create(
    configuration: ILaunchConfiguration,
    progressMonitor: IProgressMonitor
  ): Simulation {
    val threesimSimulationParameters = getThreesimSimulationParametersFromLaunchConfiguration(configuration)

    val logOutputProvider = LogOutputProviderImpl.fromLaunchConfiguration(
      ThreesimSerializers.json,
      configuration,
    )

    return when (simulationParameters.simulationType) {
      SimulationType.Single -> {
        ThreesimSingleSimulation(
          createBlockchainSystemFactory(),
          logOutputProvider,
          simulationParameters.maxAllowedBlockchainLength,
          simulationParameters as SingleSimulationParameters,
          threesimSimulationParameters,
        )
      }

      SimulationType.MonteCarlo -> {
        ThreesimMonteCarloSimulation(
          MonteCarloSimulationProgressMonitorAdapter(progressMonitor),
          createBlockchainSystemFactory(),
          logOutputProvider,
          simulationParameters.maxAllowedBlockchainLength,
          simulationParameters as MonteCarloSimulationParameters,
          threesimSimulationParameters,
        )
      }
    }
  }

  private fun getThreesimSimulationParametersFromLaunchConfiguration(
    configuration: ILaunchConfiguration
  ): ThreesimSimulationParameters {
    return ThreesimSimulationParameters(
      failureThroughputThreshold = configuration.getAttribute(
        ThreesimAttributes.FAILURE_THROUGHPUT_THRESHOLD,
        ThreesimAttributes.FAILURE_THROUGHPUT_THRESHOLD_DEFAULT
      ).toDouble(), // trx / s
      shannonEntropyK = configuration.getAttribute(
        ThreesimAttributes.SHANNON_ENTROPY_K,
        ThreesimAttributes.SHANNON_ENTROPY_K_DEFAULT
      ).toDouble(), // 0.0..1.0
      nakamotoCoefficientThreshold = configuration.getAttribute(
        ThreesimAttributes.NAKAMOTO_COEFFICIENT_THRESHOLD,
        ThreesimAttributes.NAKAMOTO_COEFFICIENT_THRESHOLD_DEFAULT
      ).toDouble(), // 0.0 .. 100.0 %
      reliabilityObservationTimespan = configuration.getAttribute(
        ThreesimAttributes.RELIABILITY_OBSERVATION_TIMESPAN,
        ThreesimAttributes.RELIABILITY_OBSERVATION_TIMESPAN_DEFAULT
      ).toDouble() // hours
    )
  }

  private fun createBlockchainSystemFactory(): ThreesimBlockchainSystemFactory {
    val designModelLoader = BlockchainSystemModelLoader()
    val designBlockchainSystem = designModelLoader.load(simulationParameters.blockchainSystemModelFilePath)

    return when (val networkTopology = designBlockchainSystem.network.topology) {
      is ExplicitNetworkTopology -> {
        ExplicitNetworkBlockchainSystemFactory(
          designBlockchainSystem,
          networkTopology
        )
      }

      is ConnectedSubgraphsNetworkTopology -> {
        ConnectedSubgraphNetworkBlockchainSystemFactory(
          designBlockchainSystem,
          networkTopology
        )
      }

      else -> throw IllegalArgumentException("Unknown network topology type: ${networkTopology::class.java.simpleName}")
    }
  }

}

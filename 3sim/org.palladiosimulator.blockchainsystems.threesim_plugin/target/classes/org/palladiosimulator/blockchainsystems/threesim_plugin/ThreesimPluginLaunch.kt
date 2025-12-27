package org.palladiosimulator.blockchainsystems.threesim_plugin

import org.eclipse.debug.core.ILaunchConfiguration
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters
import org.palladiosimulator.blockchainsystems.plugin.PluginLaunch
import org.palladiosimulator.blockchainsystems.plugin.simulation.SimulationJob
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters
import org.palladiosimulator.blockchainsystems.plugin.Attributes
import org.palladiosimulator.blockchainsystems.threesim.serialization.ThreesimSerializers
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationResultSerializer

/**
 * Launch configuration delegate for the 3SIM blockchain simulator plugin.
 *
 * Launches a simulation job based on the provided configuration and metamodel.
 *
 * @author Davis Riedel
 */
class ThreesimPluginLaunch : PluginLaunch() {

  override fun launchSimulationJob(
    configuration: ILaunchConfiguration
  ) {
    val simulationParameters = getSimulationParametersFromLaunchConfiguration(configuration)

    val configurationName = configuration.name
    val simulationTypeName = when (simulationParameters.simulationType) {
      SimulationType.Single -> "Single"
      SimulationType.MonteCarlo -> "Monte-Carlo"
    }

    val job = SimulationJob(
      configuration = configuration,
      jobName = "3SIM $simulationTypeName Simulation: $configurationName",
      simulationFactory = ThreesimSimulationFactory(simulationParameters),
      simulationResultSerializer = ThreesimSimulationResultSerializer(ThreesimSerializers.json)
    )

    job.schedule()
  }

  private fun getSimulationParametersFromLaunchConfiguration(
    configuration: ILaunchConfiguration
  ): SimulationParameters {
    val simulationType = enumValueOf<SimulationType>(
      configuration.getAttribute(
        Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE,
        Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE_DEFAULT
      )
    )
    val maxAllowedBlockchainLength = configuration.getAttribute(
      Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE,
      Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE_DEFAULT
    ).toLong()
    val blockchainSystemModelFilePath = configuration.getAttribute(
      Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE,
      Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE_DEFAULT
    )

    return when (simulationType) {
      SimulationType.Single -> SingleSimulationParameters(
        maxAllowedBlockchainLength,
        blockchainSystemModelFilePath
      )

      SimulationType.MonteCarlo -> MonteCarloSimulationParameters(
        maxAllowedBlockchainLength,
        configuration.getAttribute(
          Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS,
          Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS_DEFAULT
        ).toInt(),
        blockchainSystemModelFilePath
      )
    }
  }

}

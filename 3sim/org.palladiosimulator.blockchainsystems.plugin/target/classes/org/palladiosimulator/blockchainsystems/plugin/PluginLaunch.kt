package org.palladiosimulator.blockchainsystems.plugin

import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.debug.core.ILaunch
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.model.LaunchConfigurationDelegate

/**
 * Launch configuration delegate for the Blockchain Systems plugin.
 * This class handles the launch of different simulation types.
 * Blockchain simulators like SM-SIM and 3SIM must extend this class.
 *
 * @author Yannik Sproll, Davis Riedel
 */
abstract class PluginLaunch : LaunchConfigurationDelegate() {
  @Throws(CoreException::class)
  override fun launch(
    configuration: ILaunchConfiguration,
    arg1: String?,
    arg2: ILaunch?,
    progressMonitor: IProgressMonitor?
  ) {
    launchSimulationJob(configuration);
  }

  protected abstract fun launchSimulationJob(
    configuration: ILaunchConfiguration
  );
}
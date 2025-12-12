package org.palladiosimulator.blockchainsystems.plugin.simulation.abstractions

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.debug.core.ILaunchConfiguration
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.Simulation

/**
 * Factory interface for creating instances of [Simulation].
 *
 * @author Davis Riedel
 */
interface SimulationFactory {
  /**
   * Creates a new instance of a simulation from the given launch configuration.
   *
   * @param configuration The launch configuration containing the parameters for the simulation.
   *
   * @return A new instance of [Simulation].
   */
  fun create(configuration: ILaunchConfiguration, progressMonitor: IProgressMonitor): Simulation
}
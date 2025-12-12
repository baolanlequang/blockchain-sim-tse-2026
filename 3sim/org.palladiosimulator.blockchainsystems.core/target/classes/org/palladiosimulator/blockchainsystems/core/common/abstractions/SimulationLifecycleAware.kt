package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 *
 * The @code{SimulationLifecycleAware} interface specifies lifecycle methods for
 * simulation objects.
 *
 * @author Yannik Sproll
 */
interface SimulationLifecycleAware {
  /**
   * Initializes with the specified simulation context.
   *
   * @param simulationContext simulation context for the initialization
   */
  fun initialize(simulationContext: SimulationContext)

  /**
   * Cleans up.
   */
  fun cleanup()
}
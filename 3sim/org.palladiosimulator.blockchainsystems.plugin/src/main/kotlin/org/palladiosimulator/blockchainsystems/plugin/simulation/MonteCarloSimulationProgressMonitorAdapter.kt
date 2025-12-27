package org.palladiosimulator.blockchainsystems.plugin.simulation

import org.eclipse.core.runtime.IProgressMonitor
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.MonteCarloSimulationProgressMonitor

/**
 * An adapter that implements the [MonteCarloSimulationProgressMonitor] interface
 * using an [IProgressMonitor] to report progress.
 *
 * @param progressMonitor The progress monitor to use for reporting progress.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class MonteCarloSimulationProgressMonitorAdapter(
  private val progressMonitor: IProgressMonitor?
) :
  MonteCarloSimulationProgressMonitor {
  override fun onSimulationStarted(numberOfSimulationRounds: Int) {
    if (progressMonitor != null) {
      progressMonitor.beginTask("Running Monte-Carlo Simulation", numberOfSimulationRounds)
    } else {
      System.out.println("Running Monte-Carlo Simulation with " + numberOfSimulationRounds + " simulation round(s)");
    }
    
  }

  @Synchronized
  override fun onSimulationRoundFinished() {
    if (progressMonitor != null) {
      progressMonitor.worked(1)
    }
  }

  override fun onSimulationFinished() {
    if (progressMonitor != null) {
      progressMonitor.done()
    } else {
      System.out.println("Monte-Carlo Simulation finished");
    }
  }
}
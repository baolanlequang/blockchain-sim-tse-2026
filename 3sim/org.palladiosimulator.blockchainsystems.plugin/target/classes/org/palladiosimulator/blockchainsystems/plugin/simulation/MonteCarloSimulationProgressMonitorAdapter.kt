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
  private val progressMonitor: IProgressMonitor
) :
  MonteCarloSimulationProgressMonitor {
  override fun onSimulationStarted(numberOfSimulationRounds: Int) {
    progressMonitor.beginTask("Running Monte-Carlo Simulation", numberOfSimulationRounds)
  }

  @Synchronized
  override fun onSimulationRoundFinished() {
    progressMonitor.worked(1)
  }

  override fun onSimulationFinished() {
    progressMonitor.done()
  }
}
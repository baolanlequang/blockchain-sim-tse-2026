package org.palladiosimulator.blockchainsystems.core.simulation.abstractions;

import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock
import org.palladiosimulator.blockchainsystems.core.common.SimulationContextImpl
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput
import org.palladiosimulator.blockchainsystems.core.eventcoordination.EventCoordinatorImpl
import org.palladiosimulator.blockchainsystems.core.monitoring.abstractions.SimulationMonitor
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLoggerContainerImpl

/**
 * Represents a single round of simulation.
 * This class serves as a base for more specific simulation rounds.
 *
 * @author Davis Riedel
 */
abstract class SimulationRound<M : SimulationMonitor, R : SimulationRoundResult>(
  protected val blockchainSystem: BlockchainSystem,
  protected val logOutputs: Set<TraceEventLogOutput>,
  protected val monitor: M
) {
  protected val clock = SimulationClock()
  protected val traceEventLoggerContainer = TraceEventLoggerContainerImpl()

  protected val eventCoordinator = EventCoordinatorImpl(
    clock,
    monitor
  )

  protected val context = SimulationContextImpl(
    eventCoordinator,
    clock,
    traceEventLoggerContainer,
  )

  init {
    setUpTraceEventSubscribers()
  }

  private fun setUpTraceEventSubscribers() {
    traceEventLoggerContainer.addSubscriber(monitor)
    logOutputs.forEach { traceEventLoggerContainer.addSubscriber(it) }
  }


  open fun initialize() {
    logOutputs.forEach { it.initialize() }
    monitor.initialize(blockchainSystem)
    blockchainSystem.initialize(context)
  }

  open fun cleanup() {
    logOutputs.forEach { it.cleanUp() }
  }

  open fun run(): R {
    initialize()

    // Start processing events - processing will stop if termination condition is met
    eventCoordinator.processEvents()

    cleanup()

    return createSimulationRoundResult(clock.currentTime)
  }


  abstract fun createSimulationRoundResult(finalSystemTime: Long): R

}

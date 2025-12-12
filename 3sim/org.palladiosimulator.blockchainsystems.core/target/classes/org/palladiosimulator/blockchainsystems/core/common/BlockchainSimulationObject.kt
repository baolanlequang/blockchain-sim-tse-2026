package org.palladiosimulator.blockchainsystems.core.common

import org.palladiosimulator.blockchainsystems.core.common.abstractions.*

/**
 * The `BlockchainSimulationObject` is a base class for all classes that represent parts
 * of a blockchain system and that are log origins.
 * It provides the necessary fields to hold trace event logger and simulation context
 * and implements their initialization.
 * It also can be used as a log origin.
 *
 * @param id unique identifier of the simulation object
 * @param name name of the simulation object
 *
 * @author Yannik Sproll
 * @implNote Do not call the initializeLogger method on child classes of this class. The class sets up the correct trace event logger by itself when calling initialize.
 */
abstract class BlockchainSimulationObject protected constructor(
  override val id: String,
  override val name: String
) : EventDispatchable, Traceable, TraceEventLogOrigin {
  protected lateinit var simulationContext: SimulationContext
    private set

  protected lateinit var traceEventLogger: TraceEventLogger
    private set

  protected open fun onInitialize() {
  }

  override fun initialize(simulationContext: SimulationContext) {
    this.simulationContext = simulationContext
    initializeLogger(this)
    onInitialize()
  }

  protected open fun onCleanup() {
  }

  override fun cleanup() {
    onCleanup()
  }

  override fun initializeLogger(logOrigin: TraceEventLogOrigin) {
    val loggerContainer = simulationContext.getTraceEventLoggerContainer()
    loggerContainer.createTraceEventLogger(logOrigin)
    val logger = loggerContainer.getLogger(logOrigin) ?: return
    this.traceEventLogger = logger
  }
}
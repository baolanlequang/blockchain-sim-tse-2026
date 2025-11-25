package org.palladiosimulator.blockchainsystems.core.common

import org.palladiosimulator.blockchainsystems.core.common.abstractions.*

/**
 * The `BlockchainNodeObject` is a base class for all classes that represent components
 * of a blockchain system node.
 * It provides the necessary fields to hold trace event logger and simulation context
 * and implements their initialization.
 *
 * @author Yannik Sproll
 */
abstract class BlockchainNodeObject : EventDispatchable, Traceable {
  protected lateinit var simulationContext: SimulationContext
    private set

  protected lateinit var traceEventLogger: TraceEventLogger
    private set

  protected fun onInitialize() {
  }

  override fun initialize(simulationContext: SimulationContext) {
    this.simulationContext = simulationContext
    onInitialize()
  }

  protected fun onCleanup() {
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
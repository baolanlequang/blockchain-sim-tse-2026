package org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions

import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput

interface LogOutputProvider {
  val logOutputs: MutableSet<TraceEventLogOutput>
}
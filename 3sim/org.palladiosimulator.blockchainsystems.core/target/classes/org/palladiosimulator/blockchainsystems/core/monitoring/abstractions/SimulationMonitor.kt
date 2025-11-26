package org.palladiosimulator.blockchainsystems.core.monitoring.abstractions

import org.palladiosimulator.blockchainsystems.core.eventcoordination.TerminationCondition
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystem
import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventSubscriber

interface SimulationMonitor : TraceEventSubscriber, TerminationCondition {
  fun initialize(blockchainSystem: BlockchainSystem)
}
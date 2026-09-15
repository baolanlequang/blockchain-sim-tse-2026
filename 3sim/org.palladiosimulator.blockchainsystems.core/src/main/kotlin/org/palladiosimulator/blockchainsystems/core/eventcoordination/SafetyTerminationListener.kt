package org.palladiosimulator.blockchainsystems.core.eventcoordination

/** Receives a structured reason when the event engine stops on a configured safety limit. */
interface SafetyTerminationListener {
  fun onSafetyTermination(reason: String)
}

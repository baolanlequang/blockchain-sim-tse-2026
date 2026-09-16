package org.palladiosimulator.blockchainsystems.core.scalability

/**
 * Internal control-flow exception used to stop the current event before a
 * calibrated simulator-state safety limit would be exceeded.
 */
class ScalabilityLimitExceededException(
  val terminationReason: String
) : RuntimeException(terminationReason, null, false, false)

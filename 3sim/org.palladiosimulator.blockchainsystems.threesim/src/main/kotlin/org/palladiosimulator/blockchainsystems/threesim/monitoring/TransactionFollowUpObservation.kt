package org.palladiosimulator.blockchainsystems.threesim.monitoring

import kotlinx.serialization.Serializable

/** One transaction submitted during the measurement window. */
@Serializable
data class TransactionFollowUpObservation(
  val transactionId: String,
  val submissionTimeMs: Long,
  val followUpTimeMs: Long,
  val confirmed: Boolean
)

package org.palladiosimulator.blockchainsystems.core.behavior

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent

/**
 * Marks the beginning of one selfish-mining attempt.
 *
 * A round begins when a selfish validator, while it has no active hidden lead,
 * mines the first block that it withholds from the public network. The first
 * private block hash is also the stable round identifier. The refined 3SIM
 * monitor uses these events to delimit attack attempts inside the ordinary
 * simulation runtime rather than running a separate Monte-Carlo attack engine.
 */
@Serializable
data class SelfishMiningAttackRoundStartedTraceEvent(
  override val occurrenceTime: Long,
  val roundId: String,
  val attackerNodeId: String,
  val firstPrivateBlockHash: String,
  val forkBaseHash: String
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "SelfishMiningAttackRoundStartedTraceEvent"
  }
}

/** Associates every privately mined attacker block with the active attack round. */
@Serializable
data class SelfishMiningAttackRoundPrivateBlockTraceEvent(
  override val occurrenceTime: Long,
  val roundId: String,
  val attackerNodeId: String,
  val blockHash: String,
  val privateBlockIndex: Int
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "SelfishMiningAttackRoundPrivateBlockTraceEvent"
  }
}

/**
 * Records that the attacker has exposed all currently hidden material for a
 * round. Outcome is intentionally not encoded here: success/failure is decided
 * by the simulation monitor from the honest validators' subsequent fork view.
 */
@Serializable
data class SelfishMiningAttackRoundReleasedTraceEvent(
  override val occurrenceTime: Long,
  val roundId: String,
  val attackerNodeId: String,
  val firstPrivateBlockHash: String,
  val reason: String
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "SelfishMiningAttackRoundReleasedTraceEvent"
  }
}

/** Marks an unambiguous failed round because the selfish validator abandoned its branch. */
@Serializable
data class SelfishMiningAttackRoundAbandonedTraceEvent(
  override val occurrenceTime: Long,
  val roundId: String,
  val attackerNodeId: String,
  val firstPrivateBlockHash: String,
  val reason: String
) : TraceEvent {
  override val eventType: String = EVENT_TYPE

  companion object {
    const val EVENT_TYPE = "SelfishMiningAttackRoundAbandonedTraceEvent"
  }
}

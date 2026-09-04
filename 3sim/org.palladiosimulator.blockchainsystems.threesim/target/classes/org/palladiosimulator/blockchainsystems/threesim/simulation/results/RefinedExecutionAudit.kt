package org.palladiosimulator.blockchainsystems.threesim.simulation.results

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedCreationAudit
import org.palladiosimulator.blockchainsystems.threesim.monitoring.TransactionFollowUpObservation

/** Raw and derived values needed for refined-study verification/pilot analysis. */
@Serializable
data class RefinedExecutionAudit(
  val creation: RefinedCreationAudit? = null,
  val refinedWindowEnabled: Boolean,
  val warmupBlocksPerValidator: Int,
  val measuredBlocksPerValidator: Int,
  val transactionDrainMillis: Long,
  val warmupTargetCanonicalBlocks: Int,
  val measurementTargetCanonicalBlocks: Int,
  val canonicalBlocksObservedForWindowControl: Int,
  val measurementStartTimeMs: Long,
  val measurementEndTimeMs: Long,
  val measurementDurationMs: Long,
  val executionPhaseAtTermination: String,
  val terminationReason: String,
  val measurementWindowCompleted: Boolean,
  val transactionFollowUpCompleted: Boolean,
  val totalBlockProposalsAllPhases: Int,
  val totalTransactionSubmissionsAllPhases: Int,
  val blockRateObservationTimeMs: Long,
  val transactionRateObservationTimeMs: Long,
  val measurementSubmittedTransactions: Int,
  val measurementConfirmedTransactionsInCanonicalBlocks: Int,
  val measurementCanonicalBlocks: Int,
  val measurementConfirmedBlocks: Int,
  val measurementStaleBlocks: Int,
  val measurementTransactionsConfirmedWithinDrain: Int,
  val measurementTransactionsRightCensored: Int,
  /**
   * Number of transactions used for follow-up statistics. This remains the full
   * measurement population even when the raw observation list is not serialized.
   */
  val transactionFollowUpObservationCount: Int,
  /**
   * False in normal pilot/production output. True only when the diagnostic
   * retainTransactionFollowUpObservations flag was explicitly enabled.
   */
  val transactionFollowUpObservationsRetained: Boolean,
  /**
   * Optional diagnostic detail. Production results deliberately serialize an
   * empty list here while retaining all sufficient counts and derived metrics.
   */
  val transactionFollowUpObservations: List<TransactionFollowUpObservation>,
  val proposalEntropyNormalized: Double,
  val canonicalBlockHhiNormalized: Double,
  val transactionsPerSecond: Double,
  val transactionConfirmationRatio: Double? = null,
  val staleBlockRatio: Double,
  val restrictedMeanConfirmationLatencyMs: Double? = null,
  /** Blocks mined during measurement and eligible for the P-B propagation diagnostic. */
  val measurementBlocksEligibleForPropagationT90: Int = 0,
  /** Eligible blocks that reached 90% of non-miner validators by first full-block receipt. */
  val measurementBlocksReachingPropagationT90: Int = 0,
  /** Mean mining-to-T90 propagation delay in milliseconds. */
  val meanBlockPropagationT90Ms: Double? = null,
  /** Median mining-to-T90 propagation delay in milliseconds. */
  val medianBlockPropagationT90Ms: Double? = null,
  /**
   * Runtime attack-round counts for the revised paper-defined SPSM. A round is counted only
   * when it starts inside the measurement window. Pending rounds at measurement end are
   * reported as ambiguous and excluded from the SPSM denominator.
   */
  val selfishMiningAttackRoundsStarted: Int = 0,
  val successfulSelfishMiningAttackRounds: Int = 0,
  val failedSelfishMiningAttackRounds: Int = 0,
  val ambiguousSelfishMiningAttackRounds: Int = 0,
  val unambiguousSelfishMiningAttackRounds: Int = 0,
  val selfishMiningSuccessProbability: Double? = null
)

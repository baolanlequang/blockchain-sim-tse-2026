package org.palladiosimulator.blockchainsystems.threesim.monitoring

class ThreesimSimulationMonitorState(
  val numberOfNodes: Int,
  val hashPowerPerNode: Collection<Double>,
  val blocksProposedPerNode: Collection<Int>,
  val canonicalBlocksPerNode: Collection<Int>,
  val numberOfGeographicalRegions: Int,
  val numberOfNodesPerRegion: Collection<Int>,
  val numberOfSubmittedTransactions: Int,
  val numberOfConfirmedTransactions: Int,
  val tokensHeldPerNode: List<Double>,
  val blockProposalTimeAndConfirmationTimePerConfirmedBlock: Collection<Pair<Long, Long>>,
  val meanTimeBetweenFailures: Double,
  val meanTimeToRepair: Double,
  val numberOfStaleBlocks: Int,
  val numberOfConfirmedBlocks: Int,
  val numberOfCanonicalBlocks: Int,
  val averageThroughputDuringFailure: Double,
  val averageThroughputDuringNormalOperation: Double,
  val averageConfirmationLatencyDuringFailure: Double,
  val averageConfirmationLatencyDuringNormalOperation: Double,
  val refinedWindowEnabled: Boolean = false,
  val warmupTargetCanonicalBlocks: Int = 0,
  val measurementTargetCanonicalBlocks: Int = 0,
  val canonicalBlocksObservedForWindowControl: Int = 0,
  val measurementStartTimeMs: Long = 0L,
  val measurementEndTimeMs: Long = 0L,
  val measurementDurationMs: Long = 0L,
  /** Number of measurement-window transactions included in follow-up. */
  val transactionFollowUpObservationCount: Int = 0,
  /** Number confirmed within their per-transaction T_drain deadline. */
  val measurementTransactionsConfirmedWithinDrain: Int = 0,
  /** Number right-censored at the per-transaction T_drain deadline. */
  val measurementTransactionsRightCensored: Int = 0,
  /** TCR computed from the complete follow-up population, never from a retained sample. */
  val transactionConfirmationRatio: Double? = null,
  /** Kaplan-Meier restricted mean confirmation latency through tau=T_drain. */
  val restrictedMeanConfirmationLatencyMs: Double? = null,
  /** True only when individual observations were explicitly requested for diagnostics. */
  val transactionFollowUpObservationsRetained: Boolean = false,
  /**
   * Optional diagnostic detail. Production runs keep this empty by default;
   * all scientific summary statistics above are still computed from every
   * measurement-window transaction.
   */
  val transactionFollowUpObservations: List<TransactionFollowUpObservation> = emptyList(),
  val executionPhaseAtTermination: String = "LEGACY",
  val terminationReason: String = "NOT_TERMINATED",
  val transactionFollowUpCompleted: Boolean = false,
  val totalBlockProposalsAllPhases: Int = 0,
  val totalTransactionSubmissionsAllPhases: Int = 0,
  val blockRateObservationTimeMs: Long = 0L,
  val transactionRateObservationTimeMs: Long = 0L,
  /** Blocks mined during the measurement window and therefore eligible for P-B T90. */
  val measurementBlocksEligibleForPropagationT90: Int = 0,
  /** Eligible blocks that reached first full-block receipt at 90% of non-miner validators. */
  val measurementBlocksReachingPropagationT90: Int = 0,
  /** Mean mining-to-T90 dissemination delay over eligible blocks that reached T90. */
  val meanBlockPropagationT90Ms: Double? = null,
  /** Median mining-to-T90 dissemination delay over eligible blocks that reached T90. */
  val medianBlockPropagationT90Ms: Double? = null,
  val selfishMiningAttackRoundsStarted: Int = 0,
  val successfulSelfishMiningAttackRounds: Int = 0,
  val failedSelfishMiningAttackRounds: Int = 0,
  val ambiguousSelfishMiningAttackRounds: Int = 0,
  val unambiguousSelfishMiningAttackRounds: Int = 0,
  val selfishMiningSuccessProbability: Double? = null
)

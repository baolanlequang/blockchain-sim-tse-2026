package org.palladiosimulator.blockchainsystems.threesim.simulation

import kotlinx.serialization.Serializable

/** Configurable parameters for a 3SIM simulation. */
@Serializable
class ThreesimSimulationParameters @JvmOverloads constructor(
  val failureThroughputThreshold: Double,
  val shannonEntropyK: Double,
  val nakamotoCoefficientThreshold: Double,
  val reliabilityObservationTimespan: Double,
  /** kappa_warm: canonical-chain warm-up blocks per validating node. */
  val warmupBlocksPerValidator: Int = 0,
  /** kappa_measure: measured canonical-chain blocks per validating node. */
  val measuredBlocksPerValidator: Int = 0,
  /** T_drain in milliseconds. */
  val transactionDrainMillis: Long = 0L,
  /**
   * Diagnostic-only output switch.
   *
   * FALSE (default / production): compute all transaction follow-up statistics
   * but do not retain one JSON record per measured transaction.
   *
   * TRUE (small diagnostic runs only): serialize the individual follow-up
   * observations as well as the sufficient summary statistics.
   *
   * This flag changes output volume only. It does not alter transaction
   * generation, confirmation, censoring, TCR, RMCL, or simulation timing.
   */
  val retainTransactionFollowUpObservations: Boolean = false,
  /**
   * kappa_tx_warm: transaction submission starts this many canonical blocks
   * before the measurement window begins (0 with a transaction window = start
   * at the beginning of measurement).
   */
  val transactionWarmupBlocks: Int = 0,
  /**
   * K_tx_max: transactions are submitted during the first
   * K_tx = min(K_tx_max, kappa_measure * N_V) canonical measurement blocks only.
   * 0 disables the transaction window (legacy: submit throughout).
   */
  val transactionMeasurementBlocksMax: Int = 0
) {
  val refinedWindowEnabled: Boolean
    get() = measuredBlocksPerValidator > 0
}

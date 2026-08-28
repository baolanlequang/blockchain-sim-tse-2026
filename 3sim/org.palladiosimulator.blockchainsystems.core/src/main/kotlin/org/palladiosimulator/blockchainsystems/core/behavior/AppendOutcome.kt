package org.palladiosimulator.blockchainsystems.core.behavior

/**
 * Detailed outcome of appending a block to the blockchain, as opposed to the plain
 * "is this the new longest chain" boolean returned by [BehaviorUtils.appendBlockToBlockchain].
 *
 * @author Yannik Sproll
 */
enum class AppendOutcome {
  INCLUDED,
  FORKING,
  STALE,
  ORPHAN,
  ALREADY_APPENDED,
  NOT_APPENDED
}

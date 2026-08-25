package org.palladiosimulator.blockchainsystems.threesim.monitoring

import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundAbandonedTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundPrivateBlockTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundReleasedTraceEvent
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningAttackRoundStartedTraceEvent
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType

/**
 * Runtime tracker for the paper-defined success probability of selfish mining (SPSM).
 *
 * The old 3SIM attack runner judged one complete simulation by comparing attacker revenue share
 * with hash-power share. The revised method instead defines SPSM as the share of *unambiguous
 * attack rounds* in which honest validators adopt the attacker's previously private branch.
 * This tracker keeps the old implementation's runtime/event-driven spirit while changing the
 * outcome definition to the revised method.
 *
 * Operationalization used here:
 *  - one round starts when [SelfishMiningAttackRoundStartedTraceEvent] is emitted for the first
 *    withheld block of a fresh selfish-mining attempt;
 *  - the round succeeds when a strict majority of honest validators resolve their local chain to
 *    the attack branch. We count either (a) a first private block that was observed as Forking and
 *    later becomes Included/Confirmed, (b) a later private block becoming Included/Confirmed, or
 *    (c) the first private block reaching Confirmed;
 *  - the round fails when the attacker explicitly abandons it, or when a strict majority of honest
 *    validators classify its first private block as stale;
 *  - a round that has neither outcome when the measurement window closes is ambiguous and is
 *    excluded from the SPSM denominator.
 *
 * Requiring a majority of *honest* validators avoids treating a transient local view or the
 * attackers' own view as network adoption. Counts are retained so pair-level analysis can pool
 * N_succ and N_unambiguous across R_S and R_E exactly as specified in the manuscript.
 */
class SelfishMiningAttackRoundTracker(
  private val attackerNodeIds: Set<String>,
  honestNodeIds: Set<String>
) {
  private enum class Outcome { PENDING, SUCCESS, FAILURE }

  private data class RoundState(
    val roundId: String,
    val attackerNodeId: String,
    val firstPrivateBlockHash: String,
    val forkBaseHash: String,
    val startedAtMs: Long,
    val privateBlockHashes: MutableSet<String> = linkedSetOf(),
    val honestForkSeenNodes: MutableSet<String> = linkedSetOf(),
    val honestAdoptionNodes: MutableSet<String> = linkedSetOf(),
    val honestRejectionNodes: MutableSet<String> = linkedSetOf(),
    var released: Boolean = false,
    var outcome: Outcome = Outcome.PENDING,
    var resolvedAtMs: Long? = null,
    var resolutionReason: String? = null
  )

  data class Summary(
    val startedRounds: Int,
    val successfulRounds: Int,
    val failedRounds: Int,
    val ambiguousRounds: Int,
    val unambiguousRounds: Int,
    val successProbability: Double?
  )

  private val honestNodeIds = honestNodeIds.toSet()
  private val honestMajorityThreshold = if (this.honestNodeIds.isEmpty()) Int.MAX_VALUE else this.honestNodeIds.size / 2 + 1
  private val roundsById = linkedMapOf<String, RoundState>()
  private val roundIdByPrivateBlockHash = mutableMapOf<String, String>()

  fun reset() {
    roundsById.clear()
    roundIdByPrivateBlockHash.clear()
  }

  fun onRoundStarted(event: SelfishMiningAttackRoundStartedTraceEvent) {
    if (event.attackerNodeId !in attackerNodeIds) return
    if (event.roundId in roundsById) return

    val state = RoundState(
      roundId = event.roundId,
      attackerNodeId = event.attackerNodeId,
      firstPrivateBlockHash = event.firstPrivateBlockHash,
      forkBaseHash = event.forkBaseHash,
      startedAtMs = event.occurrenceTime
    )
    state.privateBlockHashes.add(event.firstPrivateBlockHash)
    roundsById[event.roundId] = state
    roundIdByPrivateBlockHash[event.firstPrivateBlockHash] = event.roundId
  }

  fun onPrivateBlock(event: SelfishMiningAttackRoundPrivateBlockTraceEvent) {
    val state = roundsById[event.roundId] ?: return
    if (state.outcome != Outcome.PENDING) return
    if (event.attackerNodeId != state.attackerNodeId) return

    state.privateBlockHashes.add(event.blockHash)
    roundIdByPrivateBlockHash[event.blockHash] = event.roundId
  }

  fun onRoundReleased(event: SelfishMiningAttackRoundReleasedTraceEvent) {
    val state = roundsById[event.roundId] ?: return
    if (state.outcome != Outcome.PENDING) return
    if (event.attackerNodeId != state.attackerNodeId) return
    state.released = true
  }

  fun onRoundAbandoned(event: SelfishMiningAttackRoundAbandonedTraceEvent) {
    val state = roundsById[event.roundId] ?: return
    if (state.outcome != Outcome.PENDING) return
    if (event.attackerNodeId != state.attackerNodeId) return
    resolveFailure(state, event.occurrenceTime, "ATTACKER_ABANDONED:${event.reason}")
  }

  fun onBlockAppended(blockHash: String, blockType: BlockType, nodeId: String, occurrenceTime: Long) {
    if (nodeId !in honestNodeIds) return
    val state = stateForBlock(blockHash) ?: return
    if (state.outcome != Outcome.PENDING) return

    when (blockType) {
      BlockType.ForkingBlock -> {
        if (blockHash == state.firstPrivateBlockHash) {
          state.honestForkSeenNodes.add(nodeId)
        }
      }

      BlockType.IncludedBlock -> {
        maybeRecordHonestAdoption(state, blockHash, nodeId, occurrenceTime, "APPENDED_INCLUDED")
      }

      BlockType.ConfirmedBlock -> {
        // Confirmation is an especially strong adoption signal and does not require a prior
        // local Forking observation, which may have occurred before the measurement tracker saw it.
        recordHonestAdoption(state, nodeId, occurrenceTime, "APPENDED_CONFIRMED")
      }

      BlockType.StaleBlock -> {
        recordHonestRejectionIfFirstBlock(state, blockHash, nodeId, occurrenceTime, "APPENDED_STALE")
      }
    }
  }

  fun onBlockTypeChanged(
    blockHash: String,
    oldBlockType: BlockType,
    newBlockType: BlockType,
    nodeId: String,
    occurrenceTime: Long
  ) {
    if (nodeId !in honestNodeIds) return
    val state = stateForBlock(blockHash) ?: return
    if (state.outcome != Outcome.PENDING) return

    if (blockHash == state.firstPrivateBlockHash) {
      if (oldBlockType == BlockType.ForkingBlock) state.honestForkSeenNodes.add(nodeId)
      if (newBlockType == BlockType.ForkingBlock) state.honestForkSeenNodes.add(nodeId)
    }

    if (newBlockType == BlockType.StaleBlock) {
      state.honestAdoptionNodes.remove(nodeId)
      recordHonestRejectionIfFirstBlock(state, blockHash, nodeId, occurrenceTime, "TYPE_CHANGED_TO_STALE")
      return
    }

    if (newBlockType == BlockType.ConfirmedBlock) {
      recordHonestAdoption(state, nodeId, occurrenceTime, "TYPE_CHANGED_TO_CONFIRMED")
      return
    }

    if (newBlockType == BlockType.IncludedBlock) {
      val resolvedFork = oldBlockType == BlockType.ForkingBlock || nodeId in state.honestForkSeenNodes
      val laterPrivateBlock = blockHash != state.firstPrivateBlockHash
      if (resolvedFork || laterPrivateBlock) {
        recordHonestAdoption(state, nodeId, occurrenceTime, "FORK_RESOLVED_TO_ATTACK_BRANCH")
      }
    }
  }

  fun summary(): Summary {
    val successful = roundsById.values.count { it.outcome == Outcome.SUCCESS }
    val failed = roundsById.values.count { it.outcome == Outcome.FAILURE }
    val started = roundsById.size
    val unambiguous = successful + failed
    val ambiguous = started - unambiguous
    return Summary(
      startedRounds = started,
      successfulRounds = successful,
      failedRounds = failed,
      ambiguousRounds = ambiguous,
      unambiguousRounds = unambiguous,
      successProbability = if (unambiguous > 0) successful.toDouble() / unambiguous.toDouble() else null
    )
  }

  private fun stateForBlock(blockHash: String): RoundState? {
    val roundId = roundIdByPrivateBlockHash[blockHash] ?: return null
    return roundsById[roundId]
  }

  private fun maybeRecordHonestAdoption(
    state: RoundState,
    blockHash: String,
    nodeId: String,
    occurrenceTime: Long,
    reason: String
  ) {
    // The first withheld block can transiently look Included at a node that has not yet received
    // the competing honest block. Do not call that success. For the first block, require evidence
    // that this node saw the fork; for a later private block, Included already means the private
    // branch has overtaken the competing branch locally.
    val firstBlockResolvedFromFork = blockHash == state.firstPrivateBlockHash && nodeId in state.honestForkSeenNodes
    val laterPrivateBlock = blockHash != state.firstPrivateBlockHash
    if (firstBlockResolvedFromFork || laterPrivateBlock) {
      recordHonestAdoption(state, nodeId, occurrenceTime, reason)
    }
  }

  private fun recordHonestAdoption(state: RoundState, nodeId: String, occurrenceTime: Long, reason: String) {
    if (!state.released) {
      // A private block should not be visible to honest nodes before release. Keeping this guard
      // makes a malformed/legacy behavior trace ambiguous rather than falsely successful.
      return
    }

    state.honestRejectionNodes.remove(nodeId)
    state.honestAdoptionNodes.add(nodeId)
    if (state.honestAdoptionNodes.size >= honestMajorityThreshold) {
      state.outcome = Outcome.SUCCESS
      state.resolvedAtMs = occurrenceTime
      state.resolutionReason = reason
    }
  }

  private fun recordHonestRejectionIfFirstBlock(
    state: RoundState,
    blockHash: String,
    nodeId: String,
    occurrenceTime: Long,
    reason: String
  ) {
    if (blockHash != state.firstPrivateBlockHash) return
    state.honestAdoptionNodes.remove(nodeId)
    state.honestRejectionNodes.add(nodeId)
    if (state.honestRejectionNodes.size >= honestMajorityThreshold) {
      resolveFailure(state, occurrenceTime, reason)
    }
  }

  private fun resolveFailure(state: RoundState, occurrenceTime: Long, reason: String) {
    state.outcome = Outcome.FAILURE
    state.resolvedAtMs = occurrenceTime
    state.resolutionReason = reason
  }
}

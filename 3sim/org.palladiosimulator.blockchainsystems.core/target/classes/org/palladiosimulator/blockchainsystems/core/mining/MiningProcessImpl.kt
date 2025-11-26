package org.palladiosimulator.blockchainsystems.core.mining

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.stochastics.PoissonProcess
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.system.abstractions.MiningProcess
import java.util.random.RandomGenerator

/**
 * This class implements the mining process for a blockchain node.
 *
 * @param meanBlockTime the average time between blocks
 * @param randomGenerator the random generator used to generate blocks
 *
 * @author Yannik Sproll, Davis Riedel
 */
class MiningProcessImpl(
  meanBlockTime: Double,
  randomGenerator: RandomGenerator
) : BlockchainNodeObject(), MiningProcess {
  private val poissonProcess = PoissonProcess(1.0 / meanBlockTime, randomGenerator)

  private var onCreatingBlockCallback: ((Long, String) -> Block)? = null
  private var previousBlockSelectionCallback: (() -> String)? = null
  private var onBlockMinedCallback: ((Block) -> Unit)? = null

  private var isMining = false

  override fun dispatchEvent(event: Event) {
    if (event.eventType === BlockMinedEvent.EVENT_TYPE) {
      if (!isMining) return

      val blockMinedEvent = event as BlockMinedEvent

      val block = onCreatingBlockCallback?.invoke(
        blockMinedEvent.occurrenceTime,
        blockMinedEvent.previousBlockHash
      ) ?: return

      logBlockMined(block)

      notifyBlockMined(block)

      scheduleNewBlockMinedEvent()
    }
  }


  private fun scheduleNewBlockMinedEvent(): String {
    val previousBlockHash = previousBlockSelectionCallback?.invoke() ?: throw IllegalStateException(
      "Previous block selection callback must be set before scheduling a new block mined event."
    )

    simulationContext.eventCoordinator
      .raiseEvent(
        BlockMinedEvent(
          this.getNextBlockMinedEventOccurrenceTimestamp(),
          previousBlockHash,
          this
        )
      )

    return previousBlockHash
  }

  private fun getNextBlockMinedEventOccurrenceTimestamp(): Long {
    val eventCurrentTimeOffset = poissonProcess.nextPointDistance()
    return simulationContext.systemClock.currentTime + eventCurrentTimeOffset
  }

  private fun notifyBlockMined(block: Block) {
    onBlockMinedCallback?.invoke(block)
  }

  private fun cancelPendingEvent() {
    simulationContext.eventCoordinator
      .cancelEventsFor(this)
  }

  override fun startMining() {
    if (isMining) return

    scheduleNewBlockMinedEvent()
    isMining = true

    logMiningStarted()
  }

  override fun restartMining() {
    if (!isMining) {
      startMining()
      return
    }

    cancelPendingEvent()
    val previousHash = scheduleNewBlockMinedEvent()

    logMiningRestarted(previousHash)
  }

  override fun stopMining() {
    if (!isMining) return

    cancelPendingEvent()
    logMiningStopped()
  }


  override fun setOnCreatingBlockCallback(onCreatingBlockCallback: ((Long, String) -> Block)?) {
    this.onCreatingBlockCallback = onCreatingBlockCallback
  }

  override fun setPreviousBlockSelectionCallback(previousBlockSelectionCallback: (() -> String)?) {
    this.previousBlockSelectionCallback = previousBlockSelectionCallback
  }

  override fun setOnBlockMinedCallback(onBlockMinedCallback: ((Block) -> Unit)?) {
    this.onBlockMinedCallback = onBlockMinedCallback
  }


  private fun logMiningStarted() {
    if (!traceEventLogger.isEventTypeEnabled(BlockMiningStartedTraceEvent.EVENT_TYPE)) {
      return
    }

    val event = BlockMiningStartedTraceEvent(
      simulationContext.systemClock.currentTime
    )

    traceEventLogger
      .logEvent(event)
  }

  private fun logBlockMined(block: Block) {
    if (!traceEventLogger.isEventTypeEnabled(BlockMinedTraceEvent.EVENT_TYPE)) {
      return
    }

    val event = BlockMinedTraceEvent(
      simulationContext.systemClock.currentTime,
      block
    )

    traceEventLogger
      .logEvent(event)
  }

  private fun logMiningRestarted(previousHash: String) {
    if (!traceEventLogger.isEventTypeEnabled(BlockMiningRestartedTraceEvent.EVENT_TYPE)) {
      return
    }

    val event = BlockMiningRestartedTraceEvent(
      simulationContext.systemClock.currentTime,
      previousHash
    )

    traceEventLogger
      .logEvent(event)
  }

  private fun logMiningStopped() {
    if (!traceEventLogger.isEventTypeEnabled(BlockMiningStoppedTraceEvent.EVENT_TYPE)) {
      return
    }

    val event = BlockMiningStoppedTraceEvent(
      simulationContext.systemClock.currentTime,
    )

    traceEventLogger
      .logEvent(event)
  }
}
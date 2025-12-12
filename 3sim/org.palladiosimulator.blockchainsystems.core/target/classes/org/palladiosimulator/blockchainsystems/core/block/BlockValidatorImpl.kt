package org.palladiosimulator.blockchainsystems.core.block

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockValidator
import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider

/**
 * Implementation of the BlockValidator interface that handles the validation of blocks
 * in a blockchain system.
 *
 * @property blockValidationDurationProvider A provider for the duration of block validation in ms.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockValidatorImpl(
  private val blockValidationDurationProvider: ValueProvider<Long>
) : BlockchainNodeObject(),
  BlockValidator {
  private var onBlockValidatedCallback: ((Block, Boolean) -> Unit)? = null

  override fun validateBlock(block: Block) {
    val blockValidationStartedEvent = BlockValidationStartedEvent(
      simulationContext.systemClock.currentTime,
      this,
      block
    )

    simulationContext
      .eventCoordinator
      .raiseEvent(blockValidationStartedEvent)
  }

  override fun dispatchEvent(event: Event) {
    when (event.eventType) {
      BlockValidationStartedEvent.EVENT_TYPE -> handleBlockValidationStartedEvent(event as BlockValidationStartedEvent)
      BlockValidationFinishedEvent.EVENT_TYPE -> handleBlockValidationFinishedEvent(event as BlockValidationFinishedEvent)
    }
  }

  private fun handleBlockValidationStartedEvent(event: BlockValidationStartedEvent) {
    val bvhEvent = BlockValidationFinishedEvent(
      simulationContext.systemClock.currentTime + blockValidationDurationProvider.getValue(),
      this,
      event.block
    )
    simulationContext.eventCoordinator.raiseEvent(bvhEvent)
  }

  private fun handleBlockValidationFinishedEvent(event: BlockValidationFinishedEvent) {
    notifyBlockValidated(event.block)
  }

  private fun notifyBlockValidated(block: Block) {
    onBlockValidatedCallback?.invoke(block, true)
  }

  override fun setOnBlockValidatedCallback(onBlockValidatedCallback: (Block, Boolean) -> Unit) {
    this.onBlockValidatedCallback = onBlockValidatedCallback
  }
}
package org.palladiosimulator.blockchainsystems.threesim.behavior

import org.palladiosimulator.blockchainsystems.core.common.BlockchainSimulationObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import org.palladiosimulator.blockchainsystems.core.stochastics.PoissonProcess
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionFactoryImpl
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionProperties
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSubmittedEvent
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSubmittedTraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSubmissionProcess
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSubmittedCallbackSubscriber
import java.util.random.RandomGenerator
import java.util.UUID

/**
 * The process that randomly submits transactions to the blockchain system in 3SIM.
 *
 * @author Davis Riedel
 */
class ThreesimTransactionSubmissionProcess(
  id: String,
  name: String,
  meanTransactionCreationTime: Double,
  private val transactionPropertiesProvider: ValueProvider<TransactionProperties>,
) : BlockchainSimulationObject(id, name), TransactionSubmissionProcess {
  private val poissonProcess = PoissonProcess(1.0 / meanTransactionCreationTime, RandomGenerator.of("Random"))

  private var onSelectRecipientNodeIdCallback: (() -> String)? = null

  private val onTransactionSubmittedCallbackSubscribers = HashSet<TransactionSubmittedCallbackSubscriber>()

  private var isSubmittingTransactions = false

  override fun addOnTransactionSubmittedCallbackSubscriber(subscriber: TransactionSubmittedCallbackSubscriber) {
    onTransactionSubmittedCallbackSubscribers.add(subscriber)
  }

  override fun setOnSelectRecipientNodeIdCallback(onSelectRecipientNodeIdCallback: (() -> String)?) {
    this.onSelectRecipientNodeIdCallback = onSelectRecipientNodeIdCallback
  }

  override fun startTransactionSubmissionProcess() {
    if (isSubmittingTransactions) return
    isSubmittingTransactions = true
    scheduleNewTrxSubmittedEvent()
  }

  override fun stopTransactionSubmissionProcess() {
    this.isSubmittingTransactions = false
  }

  override fun dispatchEvent(event: Event) {
    if (event.eventType === TransactionSubmittedEvent.EVENT_TYPE) {
      if (!isSubmittingTransactions) return

      val e = event as TransactionSubmittedEvent

      val recipientId = onSelectRecipientNodeIdCallback?.invoke() ?: return

      val trx = createTransaction(e.occurrenceTime, recipientId)

      logTrxSubmitted(trx)

      notifyTrxSubmitted(trx)

      scheduleNewTrxSubmittedEvent()
    }
  }

  private fun createTransaction(
    creationTime: Long,
    recipientId: String
  ): Transaction {
    val trxProps = transactionPropertiesProvider.getValue()

    // NOTE: In 3SIM transactions are considered to be sent from an anonymous user
    val senderId = UUID.randomUUID().toString()

    return TransactionFactoryImpl().createTransaction(
      txId = UUID.randomUUID().toString(),
      size = trxProps.size,
      amount = trxProps.amount,
      fee = trxProps.fee,
      creationTime = creationTime,
      senderId = senderId,
      recipientId = recipientId
    )
  }

  private fun logTrxSubmitted(trx: Transaction) {
    val event = TransactionSubmittedTraceEvent(
      simulationContext.systemClock.currentTime,
      trx
    )

    traceEventLogger.logEvent(event)
  }

  private fun notifyTrxSubmitted(trx: Transaction) {
    onTransactionSubmittedCallbackSubscribers.forEach { it.onTransactionSubmitted(trx) }
  }

  private fun getNextTransactionSubmittedEventOccurrenceTimestamp(): Long {
    val eventCurrentTimeOffset = poissonProcess.nextPointDistance()
    return simulationContext.systemClock.currentTime + eventCurrentTimeOffset
  }

  private fun scheduleNewTrxSubmittedEvent() {
    simulationContext.eventCoordinator
      .raiseEvent(
        TransactionSubmittedEvent(
          getNextTransactionSubmittedEventOccurrenceTimestamp(),
          this
        )
      )
  }
}
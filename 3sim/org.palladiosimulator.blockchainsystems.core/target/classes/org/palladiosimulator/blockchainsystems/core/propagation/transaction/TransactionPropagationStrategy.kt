package org.palladiosimulator.blockchainsystems.core.propagation.transaction

import org.palladiosimulator.blockchainsystems.core.propagation.GossipPropagationStrategy
import org.palladiosimulator.blockchainsystems.core.propagation.MessageImpl
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.network.MessageDroppedTraceEvent

/**
 * Propagation strategy for transactions in a blockchain system.
 * This strategy handles the propagation of transactions through the network using a gossip protocol.
 *
 * @author Davis Riedel
 */
class TransactionPropagationStrategy : GossipPropagationStrategy<Transaction>() {
  override val INV_MESSAGE_KEY: String = "TRX_INV"
  override val GET_DATA_MESSAGE_KEY: String = "TRX_GET_DATA"
  override val ELEMENT_MESSAGE_KEY: String = "TRX_MSG"

  private val MESSAGE_HEADER_BYTE_SIZE = 24
  private val INV_MESSAGE_BYTE_SIZE = 20
  private val GET_DATA_MESSAGE_BYTE_SIZE = 20

  /*
   * Persistent per-node transaction knowledge. A transaction can leave the mempool
   * after block inclusion, but late INV/TRX_MSG deliveries for that transaction must
   * still be recognized as duplicates. Using mempool membership alone caused already
   * processed transactions to be requested and re-gossiped again, which generated
   * tens of millions of MessageReceivedEvents in the P01 pilot.
   *
   * These sets store only transaction identifiers, not Transaction objects, so they
   * preserve the scientific transaction history needed for duplicate suppression
   * without retaining heavyweight event/message graphs.
   */
  private val knownTransactionIds = HashSet<String>()
  private val announcedTransactionIds = HashSet<String>()

  /*
   * Lifecycle note: BlockchainNodeObject.onInitialize()/onCleanup() are final
   * protected hooks in this 3SIM revision and cannot be overridden here.
   * Propagation-strategy instances are created fresh for each simulation node/run,
   * so these sets start empty naturally and become unreachable when that run is
   * cleaned up. We therefore do not override the final lifecycle hooks merely to
   * clear them; doing so would fail Kotlin compilation without changing semantics.
   */


  /**
   * Announce a transaction at most once from this node. The transaction is also
   * marked known here so locally submitted transactions remain known after they
   * later leave the mempool.
   */
  override fun shouldAnnounce(element: Transaction): Boolean {
    knownTransactionIds.add(element.txId)
    return announcedTransactionIds.add(element.txId)
  }


  override fun createInvMessage(element: Transaction): Message {
    return MessageImpl(element.txId, INV_MESSAGE_KEY, MESSAGE_HEADER_BYTE_SIZE + INV_MESSAGE_BYTE_SIZE)
  }

  override fun createGetDataMessage(elementId: String): Message {
    return MessageImpl(elementId, GET_DATA_MESSAGE_KEY, MESSAGE_HEADER_BYTE_SIZE + GET_DATA_MESSAGE_BYTE_SIZE)
  }

  override fun createElementMessage(element: Transaction): Message {
    return MessageImpl(element, ELEMENT_MESSAGE_KEY, MESSAGE_HEADER_BYTE_SIZE + element.size)
  }

  override fun handleMessageDropped(
    message: Message,
    recipientNetworkEndpoint: P2PNetworkEndpoint
  ) {
    if (networkInterface == null) throw IllegalStateException("Network interface is not set for BlockPropagationStrategy.")

    val event = MessageDroppedTraceEvent(
      message,
      simulationContext.systemClock.currentTime,
      recipientNetworkEndpoint,
      networkInterface!! // save because checked above
    )
    traceEventLogger.logEvent(event)
  }

  override fun handleInvMessageReceived(
    message: Message,
    senderNetworkEndpoint: P2PNetworkEndpoint
  ) {
    val txId = message.content as String

    context?.trxMemPool?.let {
      // A transaction remains known even after confirmation removes it from the
      // mempool. This prevents delayed inventory messages from resurrecting an
      // already processed transaction and restarting the gossip cycle.
      if (knownTransactionIds.contains(txId) || it.getTransactionById(txId) != null) {
        return
      }

      networkInterface?.send(
        createGetDataMessage(txId),
        senderNetworkEndpoint
      )
    }
  }

  override fun handleGetDataMessageReceived(
    message: Message,
    senderNetworkEndpoint: P2PNetworkEndpoint
  ) {
    val txId = message.content as String

    context?.trxMemPool?.let { trxMemPool ->
      trxMemPool.getTransactionById(txId)?.let { trx ->
        networkInterface?.send(createElementMessage(trx), senderNetworkEndpoint)
        logTrxSent(trx, senderNetworkEndpoint)
      }
    }
  }

  override fun handleElementMessageReceived(
    message: Message,
    senderNetworkEndpoint: P2PNetworkEndpoint
  ) {
    val trx = message.content as Transaction

    // Multiple peers can answer overlapping inventory requests before the first
    // copy has been processed. Only the first full transaction is admitted to the
    // node behavior; later copies are protocol duplicates and must not trigger a
    // second mempool insertion or another gossip wave.
    if (!knownTransactionIds.add(trx.txId)) {
      return
    }

    logTrxReceived(trx, senderNetworkEndpoint)
    notifyTrxReceived(trx)
  }


  private fun notifyTrxReceived(transaction: Transaction) {
    onReceivedCallback?.invoke(transaction)
  }

  private fun logTrxSent(trx: Transaction, receiverNetworkEndpoint: P2PNetworkEndpoint) {
    val event = TransactionSentTraceEvent(
      simulationContext.systemClock.currentTime,
      trx,
      receiverNetworkEndpoint
    )
    traceEventLogger.logEvent(event)
  }

  private fun logTrxReceived(trx: Transaction, senderNetworkEndpoint: P2PNetworkEndpoint) {
    val event = TransactionReceivedTraceEvent(
      simulationContext.systemClock.currentTime,
      trx,
      senderNetworkEndpoint
    )
    traceEventLogger.logEvent(event)
  }
}
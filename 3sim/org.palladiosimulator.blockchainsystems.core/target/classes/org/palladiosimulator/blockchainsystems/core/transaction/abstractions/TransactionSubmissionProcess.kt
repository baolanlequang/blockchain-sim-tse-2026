package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable
import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode

/**
 * The [TransactionSubmissionProcess] generates transactions and submits them to the blockchain network.
 *
 * @author Davis Riedel
 */
interface TransactionSubmissionProcess : Traceable {

  /**
   * Multiple nodes can subscribe to this callback that is invoked when the
   * transaction submission process submits a new transaction to the network.
   */
  fun addOnTransactionSubmittedCallbackSubscriber(
    subscriber: TransactionSubmittedCallbackSubscriber
  )

  fun setOnSelectRecipientNodeIdCallback(
    onSelectRecipientNodeIdCallback: (() -> String)? = null,
  )

  /**
   * Starts the mining of new blocks.
   */
  fun startTransactionSubmissionProcess()

  /**
   * Stops the mining of new blocks.
   */
  fun stopTransactionSubmissionProcess()
}
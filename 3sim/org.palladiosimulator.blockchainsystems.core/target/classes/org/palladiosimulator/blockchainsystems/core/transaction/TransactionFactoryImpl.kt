package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionFactory

class TransactionFactoryImpl(

) : TransactionFactory {
  override fun createTransaction(
    txId: String,
    size: Int,
    creationTime: Long,
    senderId: String,
    recipientId: String,
    amount: Double,
    fee: Double
  ): Transaction {
    return TransactionImpl(
      txId,
      size,
      creationTime,
      senderId,
      recipientId,
      amount,
      fee
    )
  }
}
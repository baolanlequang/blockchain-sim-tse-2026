package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

interface TransactionSubmittedCallbackSubscriber {
  fun onTransactionSubmitted(transaction: Transaction)
}
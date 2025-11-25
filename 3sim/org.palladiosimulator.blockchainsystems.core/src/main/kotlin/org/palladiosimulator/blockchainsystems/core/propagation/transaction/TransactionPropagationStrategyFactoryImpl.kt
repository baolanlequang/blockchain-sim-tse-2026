package org.palladiosimulator.blockchainsystems.core.propagation.transaction

import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategyFactory
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

class TransactionPropagationStrategyFactoryImpl : PropagationStrategyFactory<Transaction> {
  override fun createPropagationStrategy(): PropagationStrategy<Transaction> {
    return TransactionPropagationStrategy()
  }
}
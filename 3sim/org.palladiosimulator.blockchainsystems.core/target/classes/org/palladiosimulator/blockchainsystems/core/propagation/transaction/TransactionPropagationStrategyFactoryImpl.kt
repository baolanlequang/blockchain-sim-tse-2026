package org.palladiosimulator.blockchainsystems.core.propagation.transaction

import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategy
import org.palladiosimulator.blockchainsystems.core.propagation.PropagationStrategyFactory
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.scalability.ScalabilityStateTracker

class TransactionPropagationStrategyFactoryImpl(
  private val scalabilityStateTracker: ScalabilityStateTracker? = null
) : PropagationStrategyFactory<Transaction> {
  override fun createPropagationStrategy(): PropagationStrategy<Transaction> {
    return TransactionPropagationStrategy(scalabilityStateTracker)
  }
}
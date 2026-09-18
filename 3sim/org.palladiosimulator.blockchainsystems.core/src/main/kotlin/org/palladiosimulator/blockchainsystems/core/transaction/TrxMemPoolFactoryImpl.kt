package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPoolFactory
import org.palladiosimulator.blockchainsystems.core.scalability.ScalabilityStateTracker

/**
 * @author Davis Riedel
 */
class TrxMemPoolFactoryImpl(private val scalabilityStateTracker: ScalabilityStateTracker? = null) : TrxMemPoolFactory {
  override fun createEmptyTransactionMemPool(nodeId: String): TrxMemPool {
    return TrxMemPoolImpl(nodeId, scalabilityStateTracker)
  }
}
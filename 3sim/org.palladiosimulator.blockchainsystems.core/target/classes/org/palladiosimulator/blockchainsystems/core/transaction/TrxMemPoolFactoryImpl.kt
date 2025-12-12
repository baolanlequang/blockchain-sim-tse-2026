package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPool
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TrxMemPoolFactory

/**
 * @author Davis Riedel
 */
class TrxMemPoolFactoryImpl : TrxMemPoolFactory {
  override fun createEmptyTransactionMemPool(nodeId: String): TrxMemPool {
    return TrxMemPoolImpl(nodeId)
  }
}
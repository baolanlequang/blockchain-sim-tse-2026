package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

interface TrxMemPoolFactory {
  fun createEmptyTransactionMemPool(nodeId: String): TrxMemPool
}
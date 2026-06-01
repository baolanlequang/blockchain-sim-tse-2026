package org.palladiosimulator.blockchainsystems.core.system.abstractions

interface BlockchainMaliciousNodesIdProvider {
    fun getMaliciousNodeIds(): MutableSet<String>
    fun addMaliciousNodeId(nodeId: String)
    fun getNumberOfAttacker(): Int
}
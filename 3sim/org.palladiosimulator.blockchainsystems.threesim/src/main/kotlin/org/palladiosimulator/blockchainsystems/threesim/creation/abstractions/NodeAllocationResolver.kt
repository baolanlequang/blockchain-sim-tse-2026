package org.palladiosimulator.blockchainsystems.threesim.creation.abstractions

import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeAllocation

interface NodeAllocationResolver {
  fun getNodeAllocation(nodeId: String): NodeAllocation?
}

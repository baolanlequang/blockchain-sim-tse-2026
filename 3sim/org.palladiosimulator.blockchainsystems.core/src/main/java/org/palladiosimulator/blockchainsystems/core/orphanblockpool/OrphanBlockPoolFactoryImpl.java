package org.palladiosimulator.blockchainsystems.core.orphanblockpool;

import org.palladiosimulator.blockchainsystems.core.system.abstractions.OrphanBlockPool;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.OrphanBlockPoolFactory;

public class OrphanBlockPoolFactoryImpl implements OrphanBlockPoolFactory {

	@Override
	public OrphanBlockPool createOrphanBlockPool(String nodeId) {
		return new OrphanBlockPoolImpl();
	}

}

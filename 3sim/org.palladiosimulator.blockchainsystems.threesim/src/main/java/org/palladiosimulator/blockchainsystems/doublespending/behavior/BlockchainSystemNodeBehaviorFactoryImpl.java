package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.behavior.HonestBlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory;

public class BlockchainSystemNodeBehaviorFactoryImpl implements BlockchainSystemNodeBehaviorFactory {

	@Override
	public BlockchainSystemNodeBehavior create(String nodeId) {
		return new HonestBlockchainSystemNodeBehavior();
	}

}

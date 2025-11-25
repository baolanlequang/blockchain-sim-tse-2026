package org.palladiosimulator.blockchainsystems.core.system.abstractions;

import java.util.Set;

public interface BlockchainSystemNodeTagProvider {

	/**
	 * Returns the tags for a blockchain system node with the specified id
	 * 
	 * @param nodeId the id of the blockchain system node
	 * @return the tags of the blockchain system node
	 */
	Set<String> getTags(String nodeId);
}

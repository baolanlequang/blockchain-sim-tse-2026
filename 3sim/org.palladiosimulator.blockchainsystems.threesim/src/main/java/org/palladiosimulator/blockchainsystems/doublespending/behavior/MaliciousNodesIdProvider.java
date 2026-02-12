package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.Set;

public interface MaliciousNodesIdProvider {
	Set<String> getMaliciousNodeIds();
	void addMaliciousNodeId(String nodeId);
	int getNumberOfAttacker();
}

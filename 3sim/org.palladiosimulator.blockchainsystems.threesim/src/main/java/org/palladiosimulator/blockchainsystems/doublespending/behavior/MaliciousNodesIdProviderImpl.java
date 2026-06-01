package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.Set;

import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;


public class MaliciousNodesIdProviderImpl implements BlockchainMaliciousNodesIdProvider {

    private Set<String> _maliciousNodeIds;
    private int _numberOfAttacker;

    public MaliciousNodesIdProviderImpl(Set<String> maliciousNodeIds, int numberOfAttacker) {
        _maliciousNodeIds = maliciousNodeIds;
        _numberOfAttacker = numberOfAttacker;
    }

    public Set<String> getMaliciousNodeIds() {
        return _maliciousNodeIds;
    }

    public void addMaliciousNodeId(String nodeId) {
        if (_maliciousNodeIds.size() < _numberOfAttacker) {
            _maliciousNodeIds.add(nodeId);
        }
    }

    public int getNumberOfAttacker() {
        return _numberOfAttacker;
    }
}

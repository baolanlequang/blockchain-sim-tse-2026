package org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior;

import org.palladiosimulator.blockchainsystems.core.behavior.GammaAwareHonestBlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory;

/**
 * Factory that assigns {@link SelfishMiningNodeBehavior} (Eyal &amp; Sirer's Algorithm 1) to up to
 * {@code numberOfAttacker} nodes, and {@link GammaAwareHonestBlockchainSystemNodeBehavior} to every
 * other node so that honest nodes mine on the attacker's tip with probability {@code gamma} during
 * a tie, per the paper's network-propagation-advantage parameter.
 */
public class SelfishMiningBlockchainSystemNodeBehaviorFactory implements BlockchainSystemNodeBehaviorFactory {

    private final int numberOfAttacker;
    private final double gamma;

    public SelfishMiningBlockchainSystemNodeBehaviorFactory(int numberOfAttacker, double gamma) {
        this.numberOfAttacker = numberOfAttacker;
        this.gamma = gamma;
    }

    @Override
    public BlockchainSystemNodeBehavior create(String nodeId, BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
        if (maliciousNodesIdProvider.getMaliciousNodeIds().size() < numberOfAttacker) {
            maliciousNodesIdProvider.addMaliciousNodeId(nodeId);
        }

        if (maliciousNodesIdProvider.getMaliciousNodeIds().contains(nodeId)) {
            return new SelfishMiningNodeBehavior();
        }

        // Passing the provider's live id set (not a copy): attacker assignment happens
        // incrementally as nodes are created, but this behavior only reads the set later, once
        // mining starts and all nodes (and thus the full attacker set) already exist.
        return new GammaAwareHonestBlockchainSystemNodeBehavior(maliciousNodesIdProvider.getMaliciousNodeIds(), gamma);
    }
}

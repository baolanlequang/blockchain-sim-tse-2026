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
        // The malicious node ID set is now fully populated upfront by an explicit seeded uniform
        // sample over all node IDs (see ThreesimBlockchainSystemFactory.selectAttackerNodeIds),
        // before any node's behavior is created here -- this is a pure membership check, not the
        // previous incremental "first numberOfAttacker nodes encountered" assignment.
        if (maliciousNodesIdProvider.getMaliciousNodeIds().contains(nodeId)) {
            return new SelfishMiningNodeBehavior();
        }

        // Passing the provider's live id set (not a copy): the set is already complete by the
        // time any node's behavior is created, so this is equivalent to passing a copy, but
        // avoids an unnecessary allocation per node.
        return new GammaAwareHonestBlockchainSystemNodeBehavior(maliciousNodesIdProvider.getMaliciousNodeIds(), gamma);
    }
}

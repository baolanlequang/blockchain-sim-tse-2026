package org.palladiosimulator.blockchainsystems.threesim.selfishmining.behavior;

import java.util.Random;
import java.util.random.RandomGenerator;

import org.palladiosimulator.blockchainsystems.core.behavior.GammaAwareHonestBlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.behavior.SelfishMiningNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehavior;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeBehaviorFactory;
import org.palladiosimulator.blockchainsystems.threesim.creation.RefinedExperimentRandomness;

/** Selfish-mining behavior factory with pre-sampled attacker identities. */
public class SelfishMiningBlockchainSystemNodeBehaviorFactory implements BlockchainSystemNodeBehaviorFactory {

    private final int numberOfAttacker;
    private final double gamma;
    private final long eventSeed;

    public SelfishMiningBlockchainSystemNodeBehaviorFactory(int numberOfAttacker, double gamma) {
        this(numberOfAttacker, gamma, 0L);
    }

    public SelfishMiningBlockchainSystemNodeBehaviorFactory(int numberOfAttacker, double gamma, long eventSeed) {
        this.numberOfAttacker = numberOfAttacker;
        this.gamma = gamma;
        this.eventSeed = eventSeed;
    }

    @Override
    public BlockchainSystemNodeBehavior create(
            String nodeId,
            BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {

        // Attacker identities are sampled uniformly without replacement by the
        // blockchain-system factory before node creation. Creation order must not
        // alter the attacker set.
        long nodeSeed = RefinedExperimentRandomness.deriveSeed(
                eventSeed, "selfish-mining-behavior|" + nodeId);
        RandomGenerator rng = new Random(nodeSeed);

        if (maliciousNodesIdProvider.getMaliciousNodeIds().contains(nodeId)) {
            return new SelfishMiningNodeBehavior(rng);
        }

        return new GammaAwareHonestBlockchainSystemNodeBehavior(
                maliciousNodesIdProvider.getMaliciousNodeIds(), gamma, rng);
    }
}

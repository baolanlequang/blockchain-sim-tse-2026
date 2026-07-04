package org.palladiosimulator.blockchainsystems.threesim.selfishmining.simulation;

/**
 * Per-node breakdown for a single attacker node in a selfish-mining simulation round.
 */
public record AttackerNodeResult(String nodeId, double hashPower, int rewards) {
}

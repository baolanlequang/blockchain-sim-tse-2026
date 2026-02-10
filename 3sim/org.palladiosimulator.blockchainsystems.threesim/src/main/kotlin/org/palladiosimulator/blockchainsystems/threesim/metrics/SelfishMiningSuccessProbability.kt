package org.palladiosimulator.blockchainsystems.threesim.metrics

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric

/**
 * Output metric representing the success probability of a selfish mining attack.
 *
 * P_success = successfulAttacks / (successfulAttacks + failedAttacks)
 */
data class SelfishMiningSuccessProbability(
    val value: Double
) : OutputMetric

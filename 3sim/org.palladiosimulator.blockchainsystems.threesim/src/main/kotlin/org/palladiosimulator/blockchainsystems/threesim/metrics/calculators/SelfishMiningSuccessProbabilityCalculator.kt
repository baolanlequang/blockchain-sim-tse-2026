package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.SelfishMiningSuccessProbability
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator

/**
 * Calculates the success probability of selfish mining attacks
 * across Monte-Carlo simulation rounds.
 *
 * A round is successful if the attacker's private chain is
 * adopted as the main chain by all honest validating nodes.
 */
class SelfishMiningSuccessProbabilityCalculator(
    private val successfulAttacks: Int,
    private val failedAttacks: Int
) : OutputMetricCalculator<SelfishMiningSuccessProbability> {

    override fun calculate(): SelfishMiningSuccessProbability {
        val total = successfulAttacks + failedAttacks

        val probability =
            if (total == 0) 0.0
            else successfulAttacks.toDouble() / total.toDouble()

        return SelfishMiningSuccessProbability(probability)
    }
}

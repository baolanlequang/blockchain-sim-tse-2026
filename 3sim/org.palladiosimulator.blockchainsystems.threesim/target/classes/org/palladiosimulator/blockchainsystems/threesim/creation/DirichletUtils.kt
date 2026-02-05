package org.palladiosimulator.blockchainsystems.threesim.creation

import org.apache.commons.math3.distribution.GammaDistribution
import kotlin.math.max

object DirichletUtils {

    fun calibrateAlpha(targetH: Double, N: Int): Double {
        if (N <= 1) return 1.0

        // Formula: a = (N - 1 - H^2) / (N * H^2)
        var hSquared = targetH * targetH
        if (targetH == 0.0) {
            hSquared = 0.0001 * 0.0001 // ensure hSquared > 0
        }

        val a = (N - 1 - hSquared) / (N * hSquared)
        return max(a, 0.001) // guarantee a > 0
    }

    /**
     * Simulate Dirichlet distribution using Gamma distribution.
     */
    fun generateDirichlet(alpha: Double, N: Int): DoubleArray {
        val gamma = GammaDistribution(alpha, 1.0)
        val samples = DoubleArray(N)
        var sum = 0.0

        for (i in 0 until N) {
            samples[i] = gamma.sample()
            sum += samples[i]
        }

        for (i in 0 until N) {
            samples[i] /= sum
        }

        return samples
    }
}
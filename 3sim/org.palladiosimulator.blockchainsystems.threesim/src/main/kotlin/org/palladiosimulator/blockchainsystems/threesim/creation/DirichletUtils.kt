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
     * Calibrates the symmetric Dirichlet(a,...,a) concentration parameter `a` so that the
     * EXPECTED Herfindahl-Hirschman Index (HHI) of a sampled N-part share vector equals the
     * target implied by the paper's normalized concentration H* in [0,1] (0 = perfectly equal
     * shares, 1 = fully concentrated in one node):
     *   HHI_target = 1/N + H*(1 - 1/N)
     *
     * This is distinct from [calibrateAlpha] above, which solves for a target COEFFICIENT OF
     * VARIATION instead (CV = H, not HHI = H*-derived) -- correct for that function's existing
     * bandwidth-heterogeneity callers, but not for hashing-power concentration, which the paper
     * specifies in terms of HHI. Do not repurpose [calibrateAlpha] for HHI targets and do not
     * repurpose this function for CV targets.
     *
     * Derivation: for symmetric Dirichlet(a,...,a) with N parts, Var(X_i) = (N-1)/(N^2*(N*a+1)),
     * so E[HHI] = sum_i E[X_i^2] = N*Var(X_i) + N*(1/N)^2 = 1/N + (N-1)/(N*(N*a+1)). Setting
     * E[HHI] = HHI_target and solving for `a` gives the closed-form inverse used here:
     *   a = (1 - H*) / (N * H*)
     * This is an exact analytic inversion of the same E[HHI](a, N) relationship the paper's
     * offline Monte-Carlo calibration approximates numerically -- since the mapping has a closed
     * form, no precomputed table or interpolation is needed, and there is no discretization
     * error across the continuous N/H* ranges in the LHS sweep.
     */
    fun calibrateAlphaForHHI(targetHStar: Double, N: Int): Double {
        if (N <= 1) return 1.0

        val hStar = targetHStar.coerceIn(0.0, 1.0)
        if (hStar <= 0.0) {
            // H* = 0 -> perfectly equal shares -> a -> infinity (uniform Dirichlet). Cap at a
            // large but finite value; GammaDistribution requires a finite positive shape.
            return 1.0e6
        }

        val a = (1.0 - hStar) / (N * hStar)
        // Guarantee a > 0 for GammaDistribution (H* -> 1 gives a -> 0). The floor must stay
        // well below the smallest `a` actually needed anywhere in the target N/H* range -- e.g.
        // N=999, H*=0.999 needs a ~= 1e-6; a floor of 0.001 (as used by calibrateAlpha above,
        // which never needs to go this low) would clamp and silently cap achievable
        // concentration for large-N/high-H* configurations.
        return max(a, 1.0e-9)
    }

    /**
     * Simulate Dirichlet distribution using Gamma distribution.
     */
    fun generateDirichlet(alpha: Double, N: Int): DoubleArray {
        val gamma = GammaDistribution(alpha, 1.0)
        val samples = DoubleArray(N)
        var sum: Double

        // KNOWN, DELIBERATE DEVIATION -- retry-on-degenerate-sum only, not a change to the
        // sampling distribution itself. At very small alpha (reachable via calibrateAlphaForHHI
        // for large-N, high-H* targets, e.g. N=999, H*~0.999 needs alpha~1e-6), every draw in
        // the vector can underflow to exactly 0.0 in rare cases (empirically ~0.25% of draws at
        // the CSV's most extreme corner), making sum == 0.0 and the normalization below a
        // divide-by-zero that would propagate NaN through every downstream resource-power/
        // mining-rate calculation. This was not practically reachable before calibrateAlphaForHHI
        // existed (the old 0.001 floor never sampled shape parameters this small). Retrying with
        // fresh draws only changes the observed sample sequence in this vanishingly rare
        // degenerate case; the normal path (sum > 0, the overwhelming majority of draws) is
        // unaffected.
        do {
            sum = 0.0
            for (i in 0 until N) {
                samples[i] = gamma.sample()
                sum += samples[i]
            }
        } while (sum <= 0.0 || !sum.isFinite())

        for (i in 0 until N) {
            samples[i] /= sum
        }

        return samples
    }
}
package org.palladiosimulator.blockchainsystems.threesim.creation

import java.util.random.RandomGenerator
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

/** Utilities for the normalized-concentration parameterization used by the TSE study. */
object DirichletUtils {


  /**
   * Bandwidth-only regularization fraction used by the refined study.
   *
   * WHY THIS EXISTS
   * ---------------
   * The unregularized symmetric-Dirichlet translation
   *
   *   alpha = (1-H)/(mH)
   *
   * is mathematically correct for E[H], but for large allocation dimensions it
   * can place practically all mass on only a few recipients and produce shares
   * arbitrarily close to zero. P01 exposed this directly: with N_V=141 the old
   * allocation produced node/link bandwidths as small as 10^-81/10^-86 Mbps.
   *
   * Bandwidth is a transport capacity, so such numerically positive but
   * operationally zero links make message-delivery times astronomical and cause
   * the future-event queue to grow without bound.
   *
   * We therefore mix a fixed uniform fraction eta_B into BANDWIDTH allocations
   * only. Hashing power continues to use the original Dirichlet translation.
   *
   * eta_B = 0.10 at both hierarchical bandwidth stages implies that, with the
   * study budget B_total = 2 * B_endpoint * N_V * C, every directed endpoint
   * receives at least eta_B^2 * B_endpoint = 0.01 * B_endpoint. For the fixed
   * B_endpoint=1000 Mbps this is a 10 Mbps guaranteed endpoint floor.
   *
   * This constant must be reported as a fixed simulation setting and kept
   * unchanged across the pilot and all subsequent experiments.
   */
  const val BANDWIDTH_UNIFORM_FLOOR_FRACTION: Double = 0.10

  /**
   * Latent concentration target used before the uniform bandwidth mixture.
   *
   * If q is a simplex allocation and
   *
   *   p = eta/m * 1_m + (1-eta) q,
   *
   * then the normalized concentration used by the paper satisfies exactly
   *
   *   H(p) = (1-eta)^2 H(q).
   *
   * Therefore choosing E[H(q)] = H_target/(1-eta)^2 preserves the requested
   * expected normalized concentration after regularization.
   */
  @JvmStatic
  fun latentBandwidthTargetH(
    targetH: Double,
    uniformFloorFraction: Double = BANDWIDTH_UNIFORM_FLOOR_FRACTION
  ): Double {
    require(uniformFloorFraction >= 0.0 && uniformFloorFraction < 1.0) {
      "Bandwidth uniform-floor fraction must satisfy 0 <= eta_B < 1, got $uniformFloorFraction"
    }
    require(targetH >= 0.0 && targetH < 1.0) {
      "Normalized concentration H must satisfy 0 <= H < 1, got $targetH"
    }
    if (targetH == 0.0) return 0.0

    val remainingMass = 1.0 - uniformFloorFraction
    val maximumRepresentableH = remainingMass * remainingMass
    require(targetH < maximumRepresentableH) {
      "Target bandwidth H=$targetH is incompatible with eta_B=$uniformFloorFraction; " +
        "require H < (1-eta_B)^2=$maximumRepresentableH."
    }
    return targetH / maximumRepresentableH
  }

  /**
   * Symmetric-Dirichlet alpha for the latent random part of a regularized
   * bandwidth allocation.
   *
   * Algebraically:
   *
   *   alpha_B(m,H,eta)
   *     = (((1-eta)^2 - H) / (m H)).
   *
   * H=0 is handled by equal shares directly.
   */
  @JvmStatic
  fun alphaForRegularizedBandwidth(
    m: Int,
    targetH: Double,
    uniformFloorFraction: Double = BANDWIDTH_UNIFORM_FLOOR_FRACTION
  ): Double? {
    require(m >= 2) { "Bandwidth allocation dimension m must be >= 2, got $m" }
    if (targetH == 0.0) return null
    val latentH = latentBandwidthTargetH(targetH, uniformFloorFraction)
    return alphaFromNormalizedConcentration(m, latentH)
  }

  /**
   * Draw a bandwidth allocation that:
   *   1) sums to one;
   *   2) has E[H] equal to the requested H;
   *   3) gives every recipient at least eta_B/m of the allocation.
   *
   * No clipping or post-hoc floor is applied. The floor is part of the
   * distribution itself, so budget conservation and the target expected H are
   * preserved analytically.
   */
  @JvmStatic
  fun drawRegularizedBandwidthShares(
    m: Int,
    targetH: Double,
    randomGenerator: RandomGenerator,
    uniformFloorFraction: Double = BANDWIDTH_UNIFORM_FLOOR_FRACTION
  ): DoubleArray {
    require(m >= 2) { "Bandwidth allocation dimension m must be >= 2, got $m" }
    if (targetH == 0.0) return DoubleArray(m) { 1.0 / m.toDouble() }

    val alpha = alphaForRegularizedBandwidth(m, targetH, uniformFloorFraction)
      ?: error("Regularized bandwidth alpha unexpectedly absent for H=$targetH")
    val latent = generateDirichlet(alpha, m, randomGenerator)

    val uniformShare = uniformFloorFraction / m.toDouble()
    val randomMass = 1.0 - uniformFloorFraction
    val result = DoubleArray(m) { i -> uniformShare + randomMass * latent[i] }

    // Strong numerical audits: this function must never silently alter the
    // represented budget or violate its analytical lower bound.
    require(kotlin.math.abs(result.sum() - 1.0) <= 1e-12) {
      "Regularized bandwidth shares do not sum to one: ${result.sum()}"
    }
    val tolerance = 1e-15
    require(result.all { it + tolerance >= uniformShare }) {
      "Regularized bandwidth allocation violated its eta_B/m lower bound."
    }
    return result
  }

  /**
   * Translate the requested expected normalized concentration H into the
   * concentration of a symmetric Dirichlet distribution.
   *
   * For m recipients and p ~ Dir(alpha * 1_m),
   * E[H(p)] = 1 / (m * alpha + 1).  Solving for alpha gives
   * alpha = (1-H)/(mH).  H=0 is handled by assigning equal shares directly.
   */
  @JvmStatic
  fun alphaFromNormalizedConcentration(m: Int, h: Double): Double? {
    require(m >= 2) { "Dirichlet allocation dimension m must be >= 2, got $m" }
    require(h >= 0.0 && h < 1.0) { "Normalized concentration H must satisfy 0 <= H < 1, got $h" }
    if (h == 0.0) return null
    return (1.0 - h) / (m.toDouble() * h)
  }

  /** Backward-compatible name. New code should use [alphaFromNormalizedConcentration]. */
  @JvmStatic
  fun calibrateAlpha(targetH: Double, N: Int): Double {
    return alphaFromNormalizedConcentration(N, targetH)
      ?: throw IllegalArgumentException("H=0 has no finite Dirichlet alpha; use equal shares directly")
  }

  /**
   * Draw unregularized shares using the original symmetric-Dirichlet translation.
   *
   * In the refined simulator this remains the hashing-power allocation rule.
   * Bandwidth MUST use [drawRegularizedBandwidthShares] instead, because
   * transport capacities require the documented positive uniform component.
   */
  @JvmStatic
  fun drawShares(m: Int, h: Double, randomGenerator: RandomGenerator): DoubleArray {
    val alpha = alphaFromNormalizedConcentration(m, h)
    if (alpha == null) return DoubleArray(m) { 1.0 / m.toDouble() }

    val shares = generateDirichlet(alpha, m, randomGenerator)

    // Very small Gamma draws can underflow to exactly zero in Double arithmetic
    // when alpha is small. Mathematically, Dirichlet shares remain strictly
    // positive. Restore only those unrepresentable tails to a negligible positive
    // value and renormalize; this leaves the sampled concentration unchanged at
    // any practically observable precision while keeping mining intervals finite.
    val numericalFloor = 1e-300
    var adjusted = false
    for (i in shares.indices) {
      if (shares[i] <= 0.0) {
        shares[i] = numericalFloor
        adjusted = true
      }
    }
    if (adjusted) {
      val total = shares.sum()
      for (i in shares.indices) shares[i] /= total
    }

    return shares
  }

  /** Symmetric Dirichlet draw using deterministic Gamma(shape=alpha, scale=1) variates. */
  @JvmStatic
  fun generateDirichlet(alpha: Double, n: Int, randomGenerator: RandomGenerator): DoubleArray {
    require(alpha > 0.0 && alpha.isFinite()) { "Dirichlet alpha must be finite and > 0, got $alpha" }
    require(n >= 2) { "Dirichlet dimension must be >= 2, got $n" }

    val samples = DoubleArray(n)
    var sum = 0.0
    for (i in 0 until n) {
      val sample = sampleGamma(alpha, randomGenerator)
      samples[i] = sample
      sum += sample
    }
    require(sum > 0.0 && sum.isFinite()) { "Invalid Dirichlet normalization sum: $sum" }
    for (i in samples.indices) samples[i] /= sum
    return samples
  }

  /**
   * Legacy overload retained for source compatibility. It is deliberately not
   * used by the refined experiment because it cannot provide reproducible
   * stream separation.
   */
  @JvmStatic
  fun generateDirichlet(alpha: Double, N: Int): DoubleArray {
    return generateDirichlet(alpha, N, java.util.Random())
  }

  /** Compute H(p) = (m * sum(p_i^2) - 1)/(m-1). */
  @JvmStatic
  fun normalizedConcentration(shares: DoubleArray): Double {
    require(shares.size >= 2) { "At least two shares are required" }
    val sum = shares.sum()
    require(sum > 0.0) { "Shares must have positive sum" }
    val normalizedSquareSum = shares.sumOf { (it / sum) * (it / sum) }
    val m = shares.size.toDouble()
    return (m * normalizedSquareSum - 1.0) / (m - 1.0)
  }

  /** Marsaglia-Tsang Gamma sampler with shape > 0 and unit scale. */
  private fun sampleGamma(shape: Double, rng: RandomGenerator): Double {
    if (shape < 1.0) {
      // Boosting identity: Gamma(a) = Gamma(a+1) * U^(1/a), 0<a<1.
      var u = rng.nextDouble()
      while (u <= 0.0) u = rng.nextDouble()
      return sampleGamma(shape + 1.0, rng) * u.pow(1.0 / shape)
    }

    val d = shape - 1.0 / 3.0
    val c = 1.0 / sqrt(9.0 * d)
    while (true) {
      val x = rng.nextGaussian()
      var v = 1.0 + c * x
      if (v <= 0.0) continue
      v *= v * v
      val u = rng.nextDouble()
      val x2 = x * x
      if (u < 1.0 - 0.0331 * x2 * x2) return d * v
      if (ln(u) < 0.5 * x2 + d * (1.0 - v + ln(v))) return d * v
    }
  }
}

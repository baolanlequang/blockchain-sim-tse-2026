package org.palladiosimulator.blockchainsystems.threesim.creation

import java.util.Random
import java.util.UUID
import java.util.random.RandomGenerator

/**
 * Deterministic random-stream derivation for the refined TSE experiment.
 *
 * The paper distinguishes structural/network randomness from event-timing
 * randomness.  All streams that determine node placement, topology, resource
 * allocations, and adversarial identities are derived from [networkSeed].
 * Streams that determine block/transaction timing and other per-replication
 * stochastic events are derived from [eventSeed].
 *
 * A fresh generator is returned for every named stream.  Consequently, adding
 * draws to one mechanism does not silently perturb the sequences used by other
 * mechanisms, which makes pilot and final runs reproducible and auditable.
 */
class RefinedExperimentRandomness(
  val networkSeed: Long,
  val eventSeed: Long
) {
  fun network(label: String): RandomGenerator = Random(deriveSeed(networkSeed, label))

  fun event(label: String): RandomGenerator = Random(deriveSeed(eventSeed, label))

  fun networkForNode(label: String, nodeId: String): RandomGenerator =
    Random(deriveSeed(networkSeed, "$label|$nodeId"))

  fun eventForNode(label: String, nodeId: String): RandomGenerator =
    Random(deriveSeed(eventSeed, "$label|$nodeId"))

  fun deterministicUuid(label: String, ordinal: Long): String {
    val rng = Random(deriveSeed(eventSeed, "$label|$ordinal"))
    return UUID(rng.nextLong(), rng.nextLong()).toString()
  }

  companion object {
    /** Stable 64-bit derivation independent of JVM hashCode randomization. */
    @JvmStatic
    fun deriveSeed(baseSeed: Long, label: String): Long {
      // FNV-1a over UTF-8 label followed by SplitMix64 finalization.
      var h = 0xcbf29ce484222325UL.toLong() xor baseSeed
      for (b in label.toByteArray(Charsets.UTF_8)) {
        h = h xor (b.toLong() and 0xffL)
        h *= 0x100000001b3UL.toLong()
      }
      var z = h + 0x9E3779B97F4A7C15UL.toLong()
      z = (z xor (z ushr 30)) * 0xBF58476D1CE4E5B9UL.toLong()
      z = (z xor (z ushr 27)) * 0x94D049BB133111EBUL.toLong()
      return z xor (z ushr 31)
    }
  }
}

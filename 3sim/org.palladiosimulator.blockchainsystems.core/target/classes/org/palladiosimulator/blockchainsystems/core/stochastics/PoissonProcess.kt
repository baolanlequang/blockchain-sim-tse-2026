package org.palladiosimulator.blockchainsystems.core.stochastics

import java.util.random.RandomGenerator
import kotlin.math.ln
import kotlin.math.roundToLong

/**
 * A Poisson process that generates points in time based on a given mean rate.
 *
 * @param mean The average number of events per time unit.
 * @param randomGenerator The generator to use for generating random values.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class PoissonProcess(
  val mean: Double,
  private val randomGenerator: RandomGenerator
) {
  fun nextPointDistance(): Long {
    val nextPointDistance = -ln(1.0 - randomGenerator.nextDouble()) / this.mean
    return nextPointDistance.roundToLong()
  }
}
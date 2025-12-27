package org.palladiosimulator.blockchainsystems.threesim.metrics.calculators

import org.palladiosimulator.blockchainsystems.threesim.metrics.GeographicalDiversity
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetricCalculator
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Calculates geographical diversity
 *
 * @author Davis Riedel
 */
class GeographicalDiversityCalculator(
  private val numberOfNodes: Int,
  private val numberOfRegions: Int,
  private val numberOfNodesPerRegion: Collection<Int>
) : OutputMetricCalculator<GeographicalDiversity> {
  /**
   * Calculates the geographical diversity based on the number of nodes, regions, and nodes per region.
   */
  override fun calculate(): GeographicalDiversity {
    val gTarget = calculateGTarget()
    val gExcl = calculateGExcl()
    val gEqual = calculateGEqual()

    val result = (gExcl - gTarget - gEqual) / (gExcl - gEqual)

    return GeographicalDiversity(result)
  }

  /**
   * Calculates G_aux which is used to compute G_excl, G_target, and G_equal.
   */
  private fun calculateGAux(
    numberOfNodesPerRegion: List<Double>
  ): Double {
    val r = numberOfRegions.toDouble()

    val numberOfRegionsWithNodes = numberOfNodesPerRegion.count { it > 0 }.toDouble()

    val a = log(r, numberOfRegionsWithNodes + 1.0)
    val b = log(r, r + 1.0)
    val c = log(r, 2.0)
    val first = 2.0 - (a - b) / (c - b)

    val mu = numberOfNodes.toDouble() / r
    val sum = (0 until numberOfRegions).sumOf { i ->
      // Used so that for G_excl an array with a single element can be passed
      val noOfNodesInRegion = if (i < numberOfNodesPerRegion.size) {
        numberOfNodesPerRegion[i]
      } else {
        0.0
      }
      (noOfNodesInRegion - mu).pow(2)
    }
    val second = sqrt(sum / r)

    return first * second
  }

  /**
   * @return the geographical diversity target
   */
  private fun calculateGTarget(): Double {
    return calculateGAux(numberOfNodesPerRegion.map { it.toDouble() })
  }

  /**
   * @return the geographical diversity, if all nodes were located in a single region
   */
  private fun calculateGExcl(): Double {
    return calculateGAux(listOf(numberOfNodes.toDouble()))
  }

  /**
   * @return the geographical diversity, if all nodes are equally distributed across all regions
   */
  private fun calculateGEqual(): Double {
    return calculateGAux(
      List(numberOfRegions) { numberOfNodes.toDouble() / numberOfRegions.toDouble() }
    )
  }

  companion object : AverageOutputMetricCalculator<GeographicalDiversity>() {
    override fun getValue(metric: GeographicalDiversity): Double {
      return metric.value
    }

    override fun createResult(result: AverageCalculatorResult): AverageOutputMetric {
      return AverageOutputMetricImpl(
        name = GeographicalDiversity.NAME,
        average = result.average,
        unit = GeographicalDiversity.UNIT,
        standardDeviation = result.standardDeviation,
        coefficientOfVariation = result.coefficientOfVariation
      )
    }
  }
}
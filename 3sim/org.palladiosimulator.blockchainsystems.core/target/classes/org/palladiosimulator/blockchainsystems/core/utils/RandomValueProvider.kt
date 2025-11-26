package org.palladiosimulator.blockchainsystems.core.utils

import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import java.util.TreeMap
import java.util.random.RandomGenerator

/**
 * Provides a random value based on the provided probabilities.
 *
 * @param T the type of the values to be provided
 * @property items a sorted map of selection value borders and their corresponding values
 * @property randomGenerator the random generator used to select a value
 *
 * @author Yannik Sproll, Davis Riedel
 */
class RandomValueProvider<T> private constructor(
  private val items: TreeMap<Int, RandomValueProviderItem<T>>,
  private val randomGenerator: RandomGenerator
) : ValueProvider<T> {

  override fun getValue(): T {
    // NOTE: The not-null assertions are safe, because instances of this class can only be created
    // with the `create` method (private constructor), which ensures that the items map is not empty.

    val selectionValue = randomGenerator.nextDouble()

    var index = 0

    var currentLowerBorder = 0.0
    var currentUpperBorder = items.get(0)!!.selectionValueBorder

    while (!isInRange(selectionValue, currentLowerBorder, currentUpperBorder) && indexIsInValidRange(index)) {
      currentLowerBorder = currentUpperBorder
      currentUpperBorder = items.get(index + 1)!!.selectionValueBorder
      index++
    }

    return items.get(index)!!.value
  }

  private fun indexIsInValidRange(index: Int): Boolean {
    return index < items.size - 1
  }

  private fun isInRange(value: Double, lowerBorder: Double, upperBorder: Double): Boolean {
    return lowerBorder <= value && value < upperBorder
  }

  companion object {
    private const val RANDOM_VALUES_SUM_MAX_DEVIATION = 0.005

    fun <T> create(randomValues: Map<T, Double>, generator: RandomGenerator): RandomValueProvider<T> {
      require(randomValues.values.isNotEmpty()) { "The provided probabilities map must not be empty." }

      val probabilitiesSum = randomValues.values.sum()

      // May not be greater than 1, but if it is not exactly one it can be tolerated
      // -> Last value gets the gap as an additional probability
      require(probabilitiesSum <= 1.0) { "The sum of the provided probabilities is greater than one." }

      val deviation = 1.0 - probabilitiesSum
      require(deviation < RANDOM_VALUES_SUM_MAX_DEVIATION) { "The sum of the provided probabilities deviates more than allowed deviation maximum (0.005)." }

      val items = TreeMap<Int, RandomValueProviderItem<T>>()
      randomValues.entries.foldIndexed(0.0) { index, acc, entry ->
        val newSum = acc + entry.value
        items[index] = RandomValueProviderItem(newSum, entry.key)
        newSum
      }

      return RandomValueProvider(items, generator)
    }
  }
}
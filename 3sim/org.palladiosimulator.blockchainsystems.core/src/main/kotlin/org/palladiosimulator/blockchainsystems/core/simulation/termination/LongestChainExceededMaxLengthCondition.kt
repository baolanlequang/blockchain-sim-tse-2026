package org.palladiosimulator.blockchainsystems.core.simulation.termination

import kotlin.math.max

class LongestChainExceededMaxLengthCondition(
  private val maxAllowedLength: Long
) {
  var currentLength: Long = 0
    private set

  fun hasLengthExceeded(): Boolean {
    return currentLength >= maxAllowedLength
  }

  fun onBlockAppended(blockPosition: Long) {
    currentLength = max(blockPosition, currentLength)
  }
}
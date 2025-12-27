package org.palladiosimulator.blockchainsystems.threesim.metrics

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.AverageCalculatorResult

/**
 * Fault tolerance
 *
 * @property value Pair of throughput delta and confirmation latency delta
 *
 * @author Davis Riedel
 */
@Serializable
class FaultTolerance(
  override val value: FaultToleranceValue
) : OutputMetric<FaultToleranceValue> {
  companion object {
    const val NAME = "FaultTolerance"
  }

  override val name: String = NAME
  override val unit: String? = null
}

/**
 * Value of fault tolerance metric
 *
 * @author Davis Riedel
 */
@Serializable
class FaultToleranceValue private constructor(
  val throughputDelta: ThroughputDeltaValue,
  val confirmationLatencyDelta: ConfirmationLatencyDeltaValue,
) {
  companion object {
    fun of(
      throughputDelta: Double,
      confirmationLatencyDelta: Double
    ): FaultToleranceValue {
      return FaultToleranceValue(
        ThroughputDeltaValue.of(throughputDelta),
        ConfirmationLatencyDeltaValue.of(confirmationLatencyDelta)
      )
    }
  }
}

// The following are used for serialization

@Serializable
class ThroughputDeltaValue private constructor(
  val value: Double,
  val unit: String
) {
  companion object {
    const val UNIT = "transactions/min"

    fun of(value: Double): ThroughputDeltaValue {
      return ThroughputDeltaValue(value, UNIT)
    }
  }
}

@Serializable
class ConfirmationLatencyDeltaValue private constructor(
  val value: Double,
  val unit: String
) {
  companion object {
    const val UNIT = "ms"

    fun of(value: Double): ConfirmationLatencyDeltaValue {
      return ConfirmationLatencyDeltaValue(value, UNIT)
    }
  }
}

/**
 * Result of calculating average fault tolerance, for serialization.
 *
 * @author Davis Riedel
 */
@Serializable
class FaultToleranceAverageOutputMetric private constructor(
  val name: String,
  val average: FaultToleranceAverageOutputMetricItem
) : AverageOutputMetric {
  companion object {
    fun of(
      throughputDelta: AverageCalculatorResult,
      confirmationLatencyDelta: AverageCalculatorResult,
    ): FaultToleranceAverageOutputMetric {
      return FaultToleranceAverageOutputMetric(
        FaultTolerance.NAME,
        FaultToleranceAverageOutputMetricItem(
          AverageOutputMetricImpl(
            name = "throughputDelta",
            average = throughputDelta.average,
            unit = ThroughputDeltaValue.UNIT,
            standardDeviation = throughputDelta.standardDeviation,
            coefficientOfVariation = throughputDelta.coefficientOfVariation
          ),
          AverageOutputMetricImpl(
            name = "confirmationLatencyDelta",
            average = confirmationLatencyDelta.average,
            unit = ConfirmationLatencyDeltaValue.UNIT,
            standardDeviation = confirmationLatencyDelta.standardDeviation,
            coefficientOfVariation = confirmationLatencyDelta.coefficientOfVariation
          ),
        )
      )
    }
  }
}

/**
 * Item of the average fault tolerance result, for serialization.
 *
 * @author Davis Riedel
 */
@Serializable
data class FaultToleranceAverageOutputMetricItem(
  val throughputDelta: AverageOutputMetricImpl,
  val confirmationLatencyDelta: AverageOutputMetricImpl,
)
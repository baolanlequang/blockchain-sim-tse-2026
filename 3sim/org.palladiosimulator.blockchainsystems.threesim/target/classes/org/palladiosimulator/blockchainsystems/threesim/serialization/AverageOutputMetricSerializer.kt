package org.palladiosimulator.blockchainsystems.threesim.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceAverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl

object AverageOutputMetricSerializer : KSerializer<AverageOutputMetric> {
  override val descriptor: SerialDescriptor = buildClassSerialDescriptor("AverageOutputMetric")

  override fun serialize(encoder: Encoder, value: AverageOutputMetric) {
    when (value) {
      is AverageOutputMetricImpl -> encoder.encodeSerializableValue(serializer<AverageOutputMetricImpl>(), value)
      is FaultToleranceAverageOutputMetric -> encoder.encodeSerializableValue(
        serializer<FaultToleranceAverageOutputMetric>(),
        value
      )

      else -> throw IllegalArgumentException("Unknown average result type: ${value::class.qualifiedName}")
    }
  }

  override fun deserialize(decoder: Decoder): AverageOutputMetric {
    throw UnsupportedOperationException("Deserialization of AverageOutputMetric is not supported")
  }
}
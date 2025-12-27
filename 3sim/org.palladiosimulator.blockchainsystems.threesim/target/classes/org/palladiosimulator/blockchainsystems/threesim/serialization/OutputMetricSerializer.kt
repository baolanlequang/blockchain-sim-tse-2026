package org.palladiosimulator.blockchainsystems.threesim.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import org.palladiosimulator.blockchainsystems.threesim.metrics.FaultToleranceValue

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric

/**
 * Custom serializer for [OutputMetric] to allow serializing OutputMetric<*> instances
 * with a generic type that can be represented as a String.
 *
 * @author Davis Riedel
 */
object OutputMetricSerializer : KSerializer<OutputMetric<*>> {
  override val descriptor: SerialDescriptor = buildClassSerialDescriptor("OutputMetric") {
    element("name", serialDescriptor<String>())
    element("value", serialDescriptor<String>())
    element("unit", serialDescriptor<String>(), isOptional = true)
  }

  override fun serialize(encoder: Encoder, value: OutputMetric<*>) {
    with(encoder.beginStructure(descriptor)) {
      encodeStringElement(descriptor, 0, value.name)

      // Serialize value based on its type
      when (value.value) {
        is String -> encodeStringElement(descriptor, 1, value.value as String)
        is Double -> encodeDoubleElement(descriptor, 1, value.value as Double)
        is Int -> encodeIntElement(descriptor, 1, value.value as Int)
        is Long -> encodeLongElement(descriptor, 1, value.value as Long)
        is FaultToleranceValue -> encodeSerializableElement(
          descriptor,
          1,
          serializer<FaultToleranceValue>(),
          value.value as FaultToleranceValue
        )

        else -> throw SerializationException("Unsupported type for OutputMetric value: ${value.value::class.simpleName}")
      }

      // Serialize unit if specified
      value.unit?.let { encodeStringElement(descriptor, 2, it) }

      endStructure(descriptor)
    }
  }

  override fun deserialize(decoder: Decoder): OutputMetric<*> {
    throw UnsupportedOperationException("Deserialization of OutputMetric is not supported")
  }
}

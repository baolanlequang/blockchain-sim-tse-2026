package org.palladiosimulator.blockchainsystems.threesim.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet

object OutputMetricsSetSerializer : KSerializer<OutputMetricsSet> {
  private val delegateSerializer: KSerializer<Set<OutputMetric<*>>> =
    SetSerializer(OutputMetricSerializer)

  override val descriptor: SerialDescriptor =
    buildClassSerialDescriptor("OutputMetricsSet") {
      (0 until delegateSerializer.descriptor.elementsCount).forEach {
        element(
          delegateSerializer.descriptor.getElementName(it),
          delegateSerializer.descriptor.getElementDescriptor(it)
        )
      }
    }

  override fun serialize(encoder: Encoder, value: OutputMetricsSet) {
    delegateSerializer.serialize(encoder, value)
  }

  override fun deserialize(decoder: Decoder): OutputMetricsSet {
    throw UnsupportedOperationException("Deserialization of OutputMetricsSet is not supported")
  }
}
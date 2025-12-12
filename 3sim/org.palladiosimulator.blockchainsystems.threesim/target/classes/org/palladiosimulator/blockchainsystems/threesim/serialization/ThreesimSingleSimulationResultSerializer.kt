package org.palladiosimulator.blockchainsystems.threesim.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSingleSimulationResult

/**
 * Custom Serializer for [ThreesimSingleSimulationResult].
 *
 * @author Davis Riedel
 */
object ThreesimSingleSimulationResultSerializer : KSerializer<ThreesimSingleSimulationResult> {
  override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ThreesimSingleSimulationResult") {
    element("simulationParameters", serialDescriptor<SingleSimulationParameters>())
    element("threesimSimulationParameters", serialDescriptor<ThreesimSimulationParameters>())
    element("simulationRoundResult", serialDescriptor<OutputMetricsSet>())
  }

  override fun serialize(encoder: Encoder, value: ThreesimSingleSimulationResult) {
    with(encoder.beginStructure(descriptor)) {
      encodeSerializableElement(
        descriptor,
        0,
        serializer<SingleSimulationParameters>(),
        value.simulationParameters as SingleSimulationParameters
      )
      encodeSerializableElement(
        descriptor,
        1,
        serializer<ThreesimSimulationParameters>(),
        value.threesimSimulationParameters
      )
      encodeSerializableElement(
        descriptor,
        2,
        OutputMetricsSetSerializer,
        value.simulationRoundResult.outputMetrics
      )
      endStructure(descriptor)
    }
  }

  override fun deserialize(decoder: Decoder): ThreesimSingleSimulationResult {
    throw UnsupportedOperationException("Deserialization of ThreesimSingleSimulationResult is not supported")
  }
}

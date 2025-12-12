package org.palladiosimulator.blockchainsystems.threesim.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.utils.OutputMetricsSet
import org.palladiosimulator.blockchainsystems.threesim.simulation.ThreesimSimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimMonteCarloSimulationResult

/**
 * Custom Serializer for [ThreesimMonteCarloSimulationResult].
 *
 * @author Davis Riedel
 */
object ThreesimMonteCarloSimulationResultSerializer : KSerializer<ThreesimMonteCarloSimulationResult> {
  override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ThreesimMonteCarloSimulationResult") {
    element("simulationParameters", serialDescriptor<MonteCarloSimulationParameters>())
    element("threesimSimulationParameters", serialDescriptor<ThreesimSimulationParameters>())
    element("generalResults", serialDescriptor<OutputMetricsSet>())
    element("simulationRoundResults", serialDescriptor<List<OutputMetricsSet>>())
    element("averageSimulationRoundResult", serialDescriptor<List<AverageOutputMetric>>())
  }

  override fun serialize(encoder: Encoder, value: ThreesimMonteCarloSimulationResult) {
    with(encoder.beginStructure(descriptor)) {
      encodeSerializableElement(
        descriptor,
        0,
        serializer<MonteCarloSimulationParameters>(),
        value.simulationParameters as MonteCarloSimulationParameters
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
        value.generalResults.outputMetrics
      )
      encodeSerializableElement(
        descriptor,
        3,
        ListSerializer(OutputMetricsSetSerializer),
        value.simulationRoundResults.map { it.outputMetrics }
      )
      encodeSerializableElement(
        descriptor,
        4,
        ListSerializer(AverageOutputMetricSerializer),
        value.averageSimulationRoundResult.results
      )
      endStructure(descriptor)
    }
  }

  override fun deserialize(decoder: Decoder): ThreesimMonteCarloSimulationResult {
    throw UnsupportedOperationException("Deserialization of ThreesimMonteCarloSimulationResult is not supported")
  }
}

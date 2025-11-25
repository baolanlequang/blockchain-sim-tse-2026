package org.palladiosimulator.blockchainsystems.threesim.serialization

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.OutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.*
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockAppendedTraceEvent
import org.palladiosimulator.blockchainsystems.core.blockchain.BlockTypeChangedTraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionSubmittedTraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionStoredInMemPoolTraceEvent
import org.palladiosimulator.blockchainsystems.core.propagation.transaction.TransactionSentTraceEvent
import org.palladiosimulator.blockchainsystems.core.propagation.transaction.TransactionReceivedTraceEvent
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockSentTraceEvent
import org.palladiosimulator.blockchainsystems.core.propagation.block.BlockReceivedTraceEvent
import org.palladiosimulator.blockchainsystems.core.orphanblockpool.BlockStoredInOrphanPoolTraceEvent
import org.palladiosimulator.blockchainsystems.core.mining.BlockMiningStoppedTraceEvent
import org.palladiosimulator.blockchainsystems.core.mining.BlockMiningStartedTraceEvent
import org.palladiosimulator.blockchainsystems.core.mining.BlockMiningRestartedTraceEvent
import org.palladiosimulator.blockchainsystems.core.mining.BlockMinedTraceEvent
import org.palladiosimulator.blockchainsystems.core.network.MessageDroppedTraceEvent
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionImpl
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.BlockImpl
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationResult
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSingleSimulationResult
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimMonteCarloSimulationResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationRoundResult
import org.palladiosimulator.blockchainsystems.threesim.simulation.results.ThreesimSimulationRoundResult
import org.palladiosimulator.blockchainsystems.core.simulation.abstractions.SimulationParameters
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetric
import org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions.AverageOutputMetricImpl
import org.palladiosimulator.blockchainsystems.core.simulation.MonteCarloSimulationParameters
import org.palladiosimulator.blockchainsystems.core.simulation.SingleSimulationParameters
import kotlinx.serialization.modules.*
import kotlinx.serialization.json.Json

object ThreesimSerializers {
  val json = Json {
    prettyPrint = true
    serializersModule = SerializersModule {
      polymorphic(SimulationParameters::class) {
        subclass(SingleSimulationParameters::class)
        subclass(MonteCarloSimulationParameters::class)
      }
      polymorphic(AverageOutputMetric::class) {
        subclass(AverageOutputMetricImpl::class)
        subclass(FaultToleranceAverageOutputMetric::class)
      }
      polymorphic(OutputMetric::class) {
        subclass(AvailabilityScalability::class)
        subclass(AvailabilityScalability::class)
        subclass(AvailabilitySecurity::class)
        subclass(Consistency::class)
        subclass(FaultTolerance::class)
        subclass(GeographicalDiversity::class)
        subclass(GiniCoefficient::class)
        subclass(HerfindahlHirschmanIndex::class)
        subclass(NakamotoCoefficient::class)
        subclass(Reliability::class)
        subclass(ShannonEntropy::class)
        subclass(StaleBlockRate::class)
        subclass(TransactionThroughput::class)
      }
      polymorphic(TraceEvent::class) {
        subclass(TransactionSubmittedTraceEvent::class)
        subclass(TransactionStoredInMemPoolTraceEvent::class)
        subclass(TransactionSentTraceEvent::class)
        subclass(TransactionReceivedTraceEvent::class)
        subclass(BlockSentTraceEvent::class)
        subclass(BlockReceivedTraceEvent::class)
        subclass(BlockStoredInOrphanPoolTraceEvent::class)
        subclass(BlockMiningStoppedTraceEvent::class)
        subclass(BlockMiningStartedTraceEvent::class)
        subclass(BlockMiningRestartedTraceEvent::class)
        subclass(BlockMinedTraceEvent::class)
        subclass(BlockTypeChangedTraceEvent::class)
        subclass(BlockAppendedTraceEvent::class)
        subclass(MessageDroppedTraceEvent::class)
      }
      polymorphic(Transaction::class) {
        subclass(TransactionImpl::class)
      }
      polymorphic(Block::class) {
        subclass(BlockImpl::class)
      }
      polymorphic(SimulationResult::class) {
        subclass(ThreesimSingleSimulationResult::class)
        subclass(ThreesimMonteCarloSimulationResult::class)
      }
      polymorphic(SimulationRoundResult::class) {
        subclass(ThreesimSimulationRoundResult::class)
      }
    }
  }
}
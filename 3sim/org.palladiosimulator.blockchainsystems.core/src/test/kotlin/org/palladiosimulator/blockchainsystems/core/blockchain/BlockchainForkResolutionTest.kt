package org.palladiosimulator.blockchainsystems.core.blockchain

import org.palladiosimulator.blockchainsystems.core.block.BlockFactoryImpl
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType
import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock
import org.palladiosimulator.blockchainsystems.core.common.abstractions.*
import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Regression test: a Forking block shared by the winning branch and a losing tip
 * must not be marked Stale when the fork resolves (it used to stay Stale forever,
 * so its transactions were never confirmed and SBR was inflated).
 */
class BlockchainForkResolutionTest {
  private val factory = BlockFactoryImpl(Random(1))
  private val lastType = HashMap<String, BlockType>()

  private fun block(hash: String, parent: Block) =
    factory.createBlock(hash, parent.hash, "n", 0L, 0, emptySet())

  private fun newChain(genesis: Block): BlockchainImpl {
    val chain = BlockchainFactoryImpl(6).createBlockchain(genesis, "x") as BlockchainImpl
    val origin = object : TraceEventLogOrigin {
      override val id = "x"
      override val name = "x"
    }
    val logger = object : TraceEventLogger {
      override val logOrigin = origin
      override fun isEventTypeEnabled(eventType: String) = true
      override fun logEvent(traceEvent: TraceEvent) {
        when (traceEvent) {
          is BlockAppendedTraceEvent -> lastType[traceEvent.appendedBlock.hash] = traceEvent.appendedBlockType
          is BlockTypeChangedTraceEvent -> lastType[traceEvent.block.hash] = traceEvent.newBlockType
        }
      }
    }
    val context = object : SimulationContext {
      private val clock = SimulationClock()
      override fun getEventCoordinator(): EventCoordinator = throw UnsupportedOperationException()
      override fun getSystemClock(): SystemClock = clock
      override fun getTraceEventLoggerContainer(): TraceEventLoggerContainer = object : TraceEventLoggerContainer {
        override fun createTraceEventLogger(logOrigin: TraceEventLogOrigin) {}
        override fun getLogger(logOrigin: TraceEventLogOrigin): TraceEventLogger = logger
      }
    }
    chain.initialize(context)
    chain.initializeLogger(origin)
    return chain
  }

  @Test
  fun `shared forking ancestor of the winning branch stays canonical and confirms`() {
    val genesis = factory.createGenesisBlock()
    val chain = newChain(genesis)
    val o = block("O", genesis); chain.appendBlock(o)
    val p = block("P", o); val y1 = block("Y1", o)
    val x1 = block("X1", p); val y2 = block("Y2", y1); val x2 = block("X2", p); val x3 = block("X3", x1)

    val stale = HashSet<String>()
    for (b in listOf(p, y1, x1, y2, x2, x3)) {
      chain.appendBlock(b).blocksBecameStale.forEach { stale.add(it.hash) }
    }
    assertFalse("P" in stale, "canonical block P was reported as stale")
    assertEquals(BlockType.IncludedBlock, lastType["P"])
    assertEquals(setOf("X2", "Y1", "Y2"), stale)

    var tip = x3
    repeat(8) { i -> tip = block("X${i + 4}", tip).also { chain.appendBlock(it) } }
    assertEquals(BlockType.ConfirmedBlock, lastType["P"])
  }
}

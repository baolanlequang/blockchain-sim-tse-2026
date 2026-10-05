package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.propagation.MessageImpl
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Regression test: the block and the transaction propagation strategy share one
 * network interface, so every registered listener must receive every message.
 * (A single overwritable callback silently disabled transaction gossip.)
 */
class P2PNodeListenerTest {
  @Test
  fun `all registered listeners receive each message until removed`() {
    val node = P2PNode("a")
    val sender = P2PNode("b")
    val seenByBlocks = ArrayList<String>()
    val seenByTransactions = ArrayList<String>()
    val blockListener: (Message, P2PNetworkEndpoint) -> Unit = { m, _ -> seenByBlocks.add(m.contentType) }
    val txListener: (Message, P2PNetworkEndpoint) -> Unit = { m, _ -> seenByTransactions.add(m.contentType) }

    node.addMessageReceivedListener(txListener)
    node.addMessageReceivedListener(blockListener)
    node.onReceive(MessageImpl("t1", "TRX_INV", 44), sender)
    node.onReceive(MessageImpl("b1", "BLOCK_INV", 44), sender)
    assertEquals(listOf("TRX_INV", "BLOCK_INV"), seenByTransactions)
    assertEquals(listOf("TRX_INV", "BLOCK_INV"), seenByBlocks)

    node.removeMessageReceivedListener(txListener)
    node.onReceive(MessageImpl("t2", "TRX_INV", 44), sender)
    assertEquals(2, seenByTransactions.size)
    assertEquals(3, seenByBlocks.size)
  }
}

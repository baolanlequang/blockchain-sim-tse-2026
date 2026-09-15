package org.palladiosimulator.blockchainsystems.core.transaction

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class CompactTransactionPriorityStoreTest {
  @Test
  fun `large mixed add remove workload stays index-consistent`() {
    val store = CompactTransactionPriorityStore()
    val reference = HashMap<String, TransactionImpl>()
    val random = Random(1024)

    repeat(100_000) { step ->
      val id = "tx-${random.nextInt(30_000)}"
      when (random.nextInt(3)) {
        0, 1 -> {
          val transaction = transaction(id, step)
          val expected = reference.putIfAbsent(id, transaction) == null
          assertEquals(expected, store.add(transaction))
        }
        else -> {
          val expected = reference.remove(id)
          val actual = store.remove(id)
          assertEquals(expected?.txId, actual?.txId)
        }
      }

      if (step % 1000 == 0) {
        assertEquals(reference.size, store.size)
        repeat(100) {
          val probe = "tx-${random.nextInt(30_000)}"
          assertEquals(reference[probe]?.txId, store.get(probe)?.txId)
        }
      }
    }

    assertEquals(reference.size, store.size)
    reference.keys.forEach { id -> assertEquals(id, store.get(id)?.txId) }
  }

  @Test
  fun `remove and reinsert across many tombstones preserves lookup`() {
    val store = CompactTransactionPriorityStore()

    repeat(20_000) { i -> assertTrue(store.add(transaction("tx-$i", i))) }
    repeat(20_000) { i -> if (i % 2 == 0) assertEquals("tx-$i", store.remove("tx-$i")?.txId) }
    repeat(20_000) { i ->
      if (i % 2 == 0) {
        assertTrue(store.add(transaction("replacement-$i", i)))
        assertNull(store.get("tx-$i"))
      }
    }

    repeat(20_000) { i ->
      if (i % 2 == 0) {
        assertEquals("replacement-$i", store.get("replacement-$i")?.txId)
      } else {
        assertEquals("tx-$i", store.get("tx-$i")?.txId)
      }
    }
    assertFalse(store.add(transaction("tx-1", 1)))
  }


  @Test
  fun `priority order matches legacy comparator after rehashes and removals`() {
    val store = CompactTransactionPriorityStore()
    val reference = java.util.TreeSet<TransactionImpl> { first, second ->
      val feeRate = (second.fee / second.size).compareTo(first.fee / first.size)
      if (feeRate == 0) first.txId.compareTo(second.txId) else feeRate
    }
    val referenceById = HashMap<String, TransactionImpl>()

    repeat(25_000) { i ->
      val transaction = transaction("tx-$i", i)
      referenceById[transaction.txId] = transaction
      assertEquals(reference.add(transaction), store.add(transaction))
    }
    repeat(25_000) { i ->
      if (i % 5 == 0) {
        val expected = referenceById.remove("tx-$i")
        if (expected != null) reference.remove(expected)
        assertEquals(expected?.txId, store.remove("tx-$i")?.txId)
      }
    }

    assertEquals(reference.map { it.txId }, store.toSortedList().map { it.txId })

    val maxBytes = 100 * 500
    assertEquals(
      reference.take(100).map { it.txId },
      store.selectPrefixByTotalSize(maxBytes).map { it.txId }
    )
  }

  @Test
  fun `admission callback runs before mempool entry is committed`() {
    val store = CompactTransactionPriorityStore(
      beforeNewEntry = { throw IllegalStateException("stop-before-insert") }
    )

    assertFailsWith<IllegalStateException> { store.add(transaction("blocked", 0)) }
    assertEquals(0, store.size)
    assertNull(store.get("blocked"))
  }

  private fun transaction(id: String, n: Int) = TransactionImpl(
    txId = id,
    size = 500,
    creationTime = n.toLong(),
    senderId = "sender-$n",
    recipientId = "recipient-$n",
    amount = 1.0,
    fee = listOf(0.01, 0.02, 0.03, 0.04)[n and 3]
  )
}

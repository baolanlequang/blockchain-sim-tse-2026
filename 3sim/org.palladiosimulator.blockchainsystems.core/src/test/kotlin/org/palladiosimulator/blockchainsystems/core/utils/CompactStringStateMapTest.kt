package org.palladiosimulator.blockchainsystems.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class CompactStringStateMapTest {
  @Test
  fun `updates and duplicate inserts preserve exact state`() {
    val map = CompactStringStateMap(16)

    repeat(50_000) { i ->
      assertTrue(map.putIfAbsent("key-$i", 1))
    }
    repeat(50_000) { i ->
      assertFalse(map.putIfAbsent("key-$i", 1))
      assertEquals(1, map.put("key-$i", 2).toInt())
      assertEquals(2, map.get("key-$i").toInt())
    }
  }
  @Test
  fun `admission callback runs before a new entry is committed`() {
    val map = CompactStringStateMap(
      initialCapacity = 16,
      beforeNewEntry = { throw IllegalStateException("stop-before-insert") }
    )

    assertFailsWith<IllegalStateException> { map.putIfAbsent("blocked", 1) }
    assertEquals(CompactStringStateMap.ABSENT, map.get("blocked"))
  }

}

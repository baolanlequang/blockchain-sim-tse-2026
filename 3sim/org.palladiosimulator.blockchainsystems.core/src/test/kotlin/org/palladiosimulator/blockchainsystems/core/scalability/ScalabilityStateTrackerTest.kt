package org.palladiosimulator.blockchainsystems.core.scalability

import kotlin.test.Test
import kotlin.test.assertEquals

class ScalabilityStateTrackerTest {
  @Test
  fun `tracks aggregate and per-node live-state high water marks`() {
    val tracker = ScalabilityStateTracker()

    tracker.beforeTransactionKnowledgeEntryAdded()
    tracker.onTransactionKnowledgeEntryAdded(1)
    tracker.beforeTransactionKnowledgeEntryAdded()
    tracker.onTransactionKnowledgeEntryAdded(2)

    tracker.beforeBlockKnowledgeEntryAdded()
    tracker.onBlockKnowledgeEntryAdded(1)

    tracker.beforeMempoolEntryAdded()
    tracker.onMempoolEntryAdded(1)
    tracker.beforeMempoolEntryAdded()
    tracker.onMempoolEntryAdded(2)
    tracker.onMempoolEntryRemoved()

    tracker.beforeMeasurementTransactionEntryAdded()
    tracker.onMeasurementTransactionEntryAdded()
    tracker.resetMeasurementTransactionEntries()

    val snapshot = tracker.snapshot()
    assertEquals(2L, snapshot.currentTransactionKnowledgeEntries)
    assertEquals(2L, snapshot.maxTransactionKnowledgeEntriesObserved)
    assertEquals(2L, snapshot.maxTransactionKnowledgeEntriesPerNodeObserved)
    assertEquals(1L, snapshot.currentBlockKnowledgeEntries)
    assertEquals(1L, snapshot.maxBlockKnowledgeEntriesObserved)
    assertEquals(1L, snapshot.currentMempoolEntries)
    assertEquals(2L, snapshot.maxMempoolEntriesObserved)
    assertEquals(2L, snapshot.maxMempoolEntriesPerNodeObserved)
    assertEquals(0L, snapshot.currentMeasurementTransactionEntries)
    assertEquals(1L, snapshot.maxMeasurementTransactionEntriesObserved)
  }
}

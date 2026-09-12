package org.palladiosimulator.blockchainsystems.core.transaction

import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import java.util.PriorityQueue

/**
 * Array-backed priority store for a node's pending transactions.
 *
 * The former TreeSet allocated one red/black-tree node object for every pending
 * transaction at every validator. Under overloaded workloads this becomes the
 * dominant heap consumer. This implementation keeps the same deterministic
 * fee-rate ordering in flat arrays and maintains an exact string->heap-index
 * table, so add/remove/lookup remain logarithmic/constant without per-entry
 * collection-node objects.
 */
internal class CompactTransactionPriorityStore(initialCapacity: Int = 16) {
  private var heap: Array<Transaction?> = arrayOfNulls(maxOf(16, initialCapacity))
  private var heapSize: Int = 0
  private val indexById = StringIntIndex(initialCapacity)

  val size: Int get() = heapSize

  fun add(transaction: Transaction): Boolean {
    if (indexById.get(transaction.txId) >= 0) return false
    ensureHeapCapacity(heapSize + 1)
    heap[heapSize] = transaction
    indexById.put(transaction.txId, heapSize)
    siftUp(heapSize)
    heapSize++
    return true
  }

  fun remove(txId: String): Transaction? {
    val index = indexById.remove(txId)
    if (index < 0) return null

    val removed = heap[index]
    val lastIndex = heapSize - 1
    heapSize = lastIndex
    if (index != lastIndex) {
      val moved = heap[lastIndex]!!
      heap[index] = moved
      heap[lastIndex] = null
      indexById.put(moved.txId, index)

      val parent = (index - 1) ushr 1
      if (index > 0 && comesBefore(moved, heap[parent]!!)) {
        siftUp(index)
      } else {
        siftDown(index)
      }
    } else {
      heap[lastIndex] = null
    }
    return removed
  }

  fun get(txId: String): Transaction? {
    val index = indexById.get(txId)
    return if (index >= 0) heap[index] else null
  }

  /**
   * Returns the same ordered prefix used by the legacy TreeSet/takeWhile path,
   * without materializing or sorting the entire mempool.
   */
  fun selectPrefixByTotalSize(maxTotalSize: Int): List<Transaction> {
    if (heapSize == 0 || maxTotalSize <= 0) return emptyList()

    // A binary heap is only partially ordered. Traverse its frontier with a tiny
    // auxiliary priority queue to enumerate the global order lazily. The frontier
    // is O(k), where k is the number of transactions that actually fit in a block.
    val frontier = PriorityQueue<Int> { left, right ->
      ORDER.compare(heap[left]!!, heap[right]!!)
    }
    frontier.add(0)

    val selected = ArrayList<Transaction>()
    var totalSize = 0
    while (frontier.isNotEmpty()) {
      val index = frontier.poll()
      val transaction = heap[index]!!
      val newSize = totalSize + transaction.size
      if (newSize > maxTotalSize) break

      selected.add(transaction)
      totalSize = newSize

      val left = index * 2 + 1
      val right = left + 1
      if (left < heapSize) frontier.add(left)
      if (right < heapSize) frontier.add(right)
    }
    return selected
  }

  fun toSortedList(): List<Transaction> {
    if (heapSize == 0) return emptyList()
    val result = ArrayList<Transaction>(heapSize)
    for (i in 0 until heapSize) result.add(heap[i]!!)
    result.sortWith(ORDER)
    return result
  }

  private fun ensureHeapCapacity(required: Int) {
    if (required <= heap.size) return
    heap = heap.copyOf(maxOf(required, heap.size + (heap.size ushr 1) + 1))
  }

  private fun siftUp(startIndex: Int) {
    var index = startIndex
    while (index > 0) {
      val parent = (index - 1) ushr 1
      if (!comesBefore(heap[index]!!, heap[parent]!!)) break
      swap(index, parent)
      index = parent
    }
  }

  private fun siftDown(startIndex: Int) {
    var index = startIndex
    while (true) {
      val left = index * 2 + 1
      if (left >= heapSize) return
      val right = left + 1
      var best = left
      if (right < heapSize && comesBefore(heap[right]!!, heap[left]!!)) best = right
      if (!comesBefore(heap[best]!!, heap[index]!!)) return
      swap(index, best)
      index = best
    }
  }

  private fun swap(left: Int, right: Int) {
    val leftTransaction = heap[left]!!
    val rightTransaction = heap[right]!!
    heap[left] = rightTransaction
    heap[right] = leftTransaction
    indexById.put(rightTransaction.txId, left)
    indexById.put(leftTransaction.txId, right)
  }

  private fun comesBefore(left: Transaction, right: Transaction): Boolean = ORDER.compare(left, right) < 0

  private class StringIntIndex(initialCapacity: Int) {
    private var keys: Array<String?> = arrayOfNulls(tableSizeFor(initialCapacity))
    private var values: IntArray = IntArray(keys.size) { -1 }
    private var states: ByteArray = ByteArray(keys.size)
    private var size: Int = 0
    private var used: Int = 0
    private var resizeAt: Int = maxOf(1, (keys.size * LOAD_FACTOR).toInt())

    fun get(key: String): Int {
      var index = spread(key.hashCode()) and (keys.size - 1)
      while (true) {
        when (states[index].toInt()) {
          EMPTY -> return -1
          OCCUPIED -> if (keys[index] == key) return values[index]
        }
        index = (index + 1) and (keys.size - 1)
      }
    }

    fun put(key: String, value: Int) {
      var index = spread(key.hashCode()) and (keys.size - 1)
      var firstTombstone = -1
      while (true) {
        when (states[index].toInt()) {
          EMPTY -> break
          OCCUPIED -> if (keys[index] == key) {
            values[index] = value
            return
          }
          TOMBSTONE -> if (firstTombstone < 0) firstTombstone = index
        }
        index = (index + 1) and (keys.size - 1)
      }

      if (used + 1 > resizeAt) {
        rehash(if (size * 2 < used) keys.size else keys.size shl 1)
        put(key, value)
        return
      }

      val destination = if (firstTombstone >= 0) firstTombstone else index
      if (firstTombstone < 0) used++
      keys[destination] = key
      values[destination] = value
      states[destination] = OCCUPIED.toByte()
      size++
    }

    fun remove(key: String): Int {
      var index = spread(key.hashCode()) and (keys.size - 1)
      while (true) {
        when (states[index].toInt()) {
          EMPTY -> return -1
          OCCUPIED -> if (keys[index] == key) {
            val previous = values[index]
            keys[index] = null
            values[index] = -1
            states[index] = TOMBSTONE.toByte()
            size--
            return previous
          }
        }
        index = (index + 1) and (keys.size - 1)
      }
    }

    private fun rehash(newCapacity: Int) {
      val oldKeys = keys
      val oldValues = values
      val oldStates = states
      keys = arrayOfNulls(newCapacity)
      values = IntArray(newCapacity) { -1 }
      states = ByteArray(newCapacity)
      size = 0
      used = 0
      resizeAt = maxOf(1, (newCapacity * LOAD_FACTOR).toInt())

      oldKeys.indices.forEach { oldIndex ->
        if (oldStates[oldIndex].toInt() == OCCUPIED) {
          put(oldKeys[oldIndex]!!, oldValues[oldIndex])
        }
      }
    }

    companion object {
      private const val EMPTY = 0
      private const val OCCUPIED = 1
      private const val TOMBSTONE = 2
      private const val LOAD_FACTOR = 0.65

      private fun spread(hashCode: Int): Int = hashCode xor (hashCode ushr 16)

      private fun tableSizeFor(requested: Int): Int {
        var capacity = 16
        val target = maxOf(1, requested)
        while (capacity < target / LOAD_FACTOR) capacity = capacity shl 1
        return capacity
      }
    }
  }

  companion object {
    /** Exactly the ordering used by the former TreeSet implementation. */
    private val ORDER = Comparator<Transaction> { first, second ->
      val firstRate = first.fee / first.size
      val secondRate = second.fee / second.size
      val feeRate = secondRate.compareTo(firstRate)
      if (feeRate == 0) first.txId.compareTo(second.txId) else feeRate
    }
  }
}

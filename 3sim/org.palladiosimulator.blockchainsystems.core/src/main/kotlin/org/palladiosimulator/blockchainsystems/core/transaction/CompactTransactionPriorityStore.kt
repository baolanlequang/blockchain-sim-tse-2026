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
internal class CompactTransactionPriorityStore(
  initialCapacity: Int = 16,
  private val beforeNewEntry: ((Int) -> Unit)? = null,
  private val afterNewEntry: ((Int) -> Unit)? = null,
  private val afterEntryRemoved: ((Int) -> Unit)? = null
) {
  private var heap: Array<Transaction?> = arrayOfNulls(maxOf(16, initialCapacity))
  private var heapSize: Int = 0
  private val indexById = StringIntIndex(initialCapacity)

  val size: Int get() = heapSize

  fun add(transaction: Transaction): Boolean {
    if (indexById.get(transaction.txId) >= 0) return false
    beforeNewEntry?.invoke(heapSize + 1)
    ensureHeapCapacity(heapSize + 1)
    heap[heapSize] = transaction
    indexById.put(transaction.txId, heapSize)
    siftUp(heapSize)
    heapSize++
    afterNewEntry?.invoke(heapSize)
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
      indexById.replaceIndex(moved.txId, lastIndex, index)

      val parent = (index - 1) ushr 1
      if (index > 0 && comesBefore(moved, heap[parent]!!)) {
        siftUp(index)
      } else {
        siftDown(index)
      }
    } else {
      heap[lastIndex] = null
    }
    afterEntryRemoved?.invoke(heapSize)
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

    // Locate both index slots before changing either mapping. Updating one mapping
    // first would temporarily duplicate a heap-index value and could confuse the
    // second lookup when two ids share an open-addressing probe cluster.
    indexById.swapIndices(leftTransaction.txId, left, rightTransaction.txId, right)
    heap[left] = rightTransaction
    heap[right] = leftTransaction
  }

  private fun comesBefore(left: Transaction, right: Transaction): Boolean = ORDER.compare(left, right) < 0

  /**
   * Open-addressed txId -> heap-index table.
   *
   * The heap already owns each Transaction and therefore its txId. Storing the
   * same String reference again in this index is redundant. Each occupied slot
   * stores only heapIndex + 1; lookups compare the requested id with the
   * transaction at that heap position. Zero means empty and -1 a tombstone.
   * This cuts the largest mempool index from three backing arrays to one IntArray
   * and greatly reduces peak memory during rehash.
   */
  private inner class StringIntIndex(initialCapacity: Int) {
    private var table: IntArray = IntArray(indexTableSizeFor(initialCapacity))
    private var size: Int = 0
    private var used: Int = 0
    private var resizeAt: Int = maxOf(1, (table.size * INDEX_LOAD_FACTOR).toInt())

    fun get(key: String): Int {
      var slot = spreadIndexHash(key.hashCode()) and (table.size - 1)
      while (true) {
        val encoded = table[slot]
        if (encoded == INDEX_EMPTY) return -1
        if (encoded > 0) {
          val heapIndex = encoded - 1
          val transaction = heap.getOrNull(heapIndex)
          if (transaction != null && transaction.txId == key) return heapIndex
        }
        slot = (slot + 1) and (table.size - 1)
      }
    }

    fun put(key: String, value: Int) {
      require(value >= 0) { "Heap index must be non-negative" }

      var slot = spreadIndexHash(key.hashCode()) and (table.size - 1)
      var firstTombstone = -1
      while (true) {
        val encoded = table[slot]
        when {
          encoded == INDEX_EMPTY -> break
          encoded == INDEX_TOMBSTONE -> if (firstTombstone < 0) firstTombstone = slot
          else -> {
            val heapIndex = encoded - 1
            val transaction = heap.getOrNull(heapIndex)
            if (transaction != null && transaction.txId == key) {
              table[slot] = value + 1
              return
            }
          }
        }
        slot = (slot + 1) and (table.size - 1)
      }

      if (used + 1 > resizeAt) {
        rehash(if (size * 2 < used) table.size else table.size shl 1)
        put(key, value)
        return
      }

      val destination = if (firstTombstone >= 0) firstTombstone else slot
      if (firstTombstone < 0) used++
      table[destination] = value + 1
      size++
    }

    fun remove(key: String): Int {
      var slot = spreadIndexHash(key.hashCode()) and (table.size - 1)
      while (true) {
        val encoded = table[slot]
        if (encoded == INDEX_EMPTY) return -1
        if (encoded > 0) {
          val heapIndex = encoded - 1
          val transaction = heap.getOrNull(heapIndex)
          if (transaction != null && transaction.txId == key) {
            table[slot] = INDEX_TOMBSTONE
            size--
            return heapIndex
          }
        }
        slot = (slot + 1) and (table.size - 1)
      }
    }

    /** Replace an existing mapping identified by its unique old heap index. */
    fun replaceIndex(key: String, oldIndex: Int, newIndex: Int) {
      val slot = findMappingSlot(key, oldIndex)
      table[slot] = newIndex + 1
    }

    /** Swap two existing mappings atomically with respect to table contents. */
    fun swapIndices(leftKey: String, leftIndex: Int, rightKey: String, rightIndex: Int) {
      val leftSlot = findMappingSlot(leftKey, leftIndex)
      val rightSlot = findMappingSlot(rightKey, rightIndex)
      check(leftSlot != rightSlot) { "Distinct heap entries resolved to the same index slot" }
      table[leftSlot] = rightIndex + 1
      table[rightSlot] = leftIndex + 1
    }

    private fun findMappingSlot(key: String, heapIndex: Int): Int {
      var slot = spreadIndexHash(key.hashCode()) and (table.size - 1)
      val encodedHeapIndex = heapIndex + 1
      while (true) {
        val encoded = table[slot]
        if (encoded == INDEX_EMPTY) {
          throw IllegalStateException("Missing transaction index for $key at heap index $heapIndex")
        }
        if (encoded == encodedHeapIndex) return slot
        slot = (slot + 1) and (table.size - 1)
      }
    }

    private fun rehash(newCapacity: Int) {
      val oldTable = table
      table = IntArray(newCapacity)
      size = 0
      used = 0
      resizeAt = maxOf(1, (newCapacity * INDEX_LOAD_FACTOR).toInt())

      for (encoded in oldTable) {
        if (encoded <= 0) continue
        val heapIndex = encoded - 1
        val transaction = heap[heapIndex]
          ?: throw IllegalStateException("Transaction index points to an empty heap slot: $heapIndex")
        put(transaction.txId, heapIndex)
      }
    }

  }

  companion object {
    private const val INDEX_EMPTY = 0
    private const val INDEX_TOMBSTONE = -1
    private const val INDEX_LOAD_FACTOR = 0.65

    private fun spreadIndexHash(hashCode: Int): Int = hashCode xor (hashCode ushr 16)

    private fun indexTableSizeFor(requested: Int): Int {
      var capacity = 16
      val target = maxOf(1, requested)
      while (capacity < target / INDEX_LOAD_FACTOR) capacity = capacity shl 1
      return capacity
    }

    /** Exactly the ordering used by the former TreeSet implementation. */
    private val ORDER = Comparator<Transaction> { first, second ->
      val firstRate = first.fee / first.size
      val secondRate = second.fee / second.size
      val feeRate = secondRate.compareTo(firstRate)
      if (feeRate == 0) first.txId.compareTo(second.txId) else feeRate
    }
  }
}

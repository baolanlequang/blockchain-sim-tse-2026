package org.palladiosimulator.blockchainsystems.core.utils

/**
 * Append-only string -> small-state table optimized for long simulator runs.
 *
 * Java HashMap has one node object per entry in addition to its bucket array. For
 * per-node gossip histories that can contain millions of identifiers, those node
 * objects dominate memory. This table stores the same information in two flat
 * arrays: one String reference and one byte per occupied slot.
 *
 * State 0 is reserved for "absent". Entries are never removed, matching the
 * lifetime of the gossip duplicate-suppression histories that use this class.
 */
class CompactStringStateMap(initialCapacity: Int = 16) {
  private var keys: Array<String?> = arrayOfNulls(tableSizeFor(initialCapacity))
  private var states: ByteArray = ByteArray(keys.size)
  private var size: Int = 0
  private var resizeAt: Int = maxOf(1, (keys.size * LOAD_FACTOR).toInt())

  fun get(key: String): Byte {
    var index = spread(key.hashCode()) and (keys.size - 1)
    while (true) {
      val current = keys[index] ?: return ABSENT
      if (current == key) return states[index]
      index = (index + 1) and (keys.size - 1)
    }
  }

  /** Stores [state] and returns the previous state (0 when absent). */
  fun put(key: String, state: Byte): Byte {
    require(state != ABSENT) { "State 0 is reserved for absent entries" }
    if (size + 1 > resizeAt) resize(keys.size shl 1)

    var index = spread(key.hashCode()) and (keys.size - 1)
    while (true) {
      val current = keys[index]
      if (current == null) {
        keys[index] = key
        states[index] = state
        size++
        return ABSENT
      }
      if (current == key) {
        val previous = states[index]
        states[index] = state
        return previous
      }
      index = (index + 1) and (keys.size - 1)
    }
  }

  /** Inserts [state] only when absent and returns true when insertion happened. */
  fun putIfAbsent(key: String, state: Byte): Boolean {
    require(state != ABSENT) { "State 0 is reserved for absent entries" }
    if (size + 1 > resizeAt) resize(keys.size shl 1)

    var index = spread(key.hashCode()) and (keys.size - 1)
    while (true) {
      val current = keys[index]
      if (current == null) {
        keys[index] = key
        states[index] = state
        size++
        return true
      }
      if (current == key) return false
      index = (index + 1) and (keys.size - 1)
    }
  }

  fun containsKey(key: String): Boolean = get(key) != ABSENT

  private fun resize(newCapacity: Int) {
    val oldKeys = keys
    val oldStates = states
    keys = arrayOfNulls(newCapacity)
    states = ByteArray(newCapacity)
    resizeAt = maxOf(1, (newCapacity * LOAD_FACTOR).toInt())

    oldKeys.indices.forEach { oldIndex ->
      val key = oldKeys[oldIndex] ?: return@forEach
      var index = spread(key.hashCode()) and (keys.size - 1)
      while (keys[index] != null) {
        index = (index + 1) and (keys.size - 1)
      }
      keys[index] = key
      states[index] = oldStates[oldIndex]
    }
  }

  companion object {
    const val ABSENT: Byte = 0
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

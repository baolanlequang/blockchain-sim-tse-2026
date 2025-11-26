package org.palladiosimulator.blockchainsystems.plugin.ui.results

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Represents a node in a JSON tree structure for displaying JSON data in a tree viewer.
 *
 * @author Davis Riedel
 */
data class JsonTreeNode(
  val key: String,
  val value: JsonElement,
  val parent: JsonTreeNode? = null
) {

  /**
   * Returns a display string for this node
   */
  fun getDisplayText(): String {
    return when (value) {
      is JsonPrimitive -> {
        when {
          value.isString -> "$key: \"${value.content}\""
          else -> "$key: ${value.content}"
        }
      }

      is JsonObject -> getDisplayTextForJsonElementWithChildren(key, value.size, "{}")
      is JsonArray -> getDisplayTextForJsonElementWithChildren(key, value.size, "[]")

      JsonNull -> "$key: null"
    }
  }

  private fun getDisplayTextForJsonElementWithChildren(key: String, size: Int, wrapper: String): String {
    val descr = if (size == 1) "item" else "items"
    return "$key: " + wrapper.first() + " $size $descr " + wrapper.last()
  }

  override fun toString(): String = getDisplayText()

}

package org.palladiosimulator.blockchainsystems.plugin.ui.results

import kotlinx.serialization.json.*
import org.eclipse.jface.viewers.ITreeContentProvider

/**
 * Content provider for displaying JSON data in a tree viewer.
 *
 * @author Davis Riedel
 */
open class JsonTreeContentProvider : ITreeContentProvider {

  override fun getElements(inputElement: Any?): Array<Any> {
    return when (inputElement) {
      is String -> {
        try {
          arrayOf(JsonTreeNode("root", Json.parseToJsonElement(inputElement)))
        } catch (e: Exception) {
          e.printStackTrace()
          arrayOf("ERROR")
        }
      }

      is JsonElement -> arrayOf(JsonTreeNode("root", inputElement))

      else -> emptyArray()
    }
  }

  override fun getChildren(parentElement: Any?): Array<Any> {
    return when (parentElement) {
      is JsonTreeNode -> getJsonElementChildren(parentElement)
      else -> emptyArray()
    }
  }

  override fun getParent(element: Any?): Any? {
    return when (element) {
      is JsonTreeNode -> element.parent
      else -> null
    }
  }

  override fun hasChildren(element: Any?): Boolean {
    return when (element) {
      is JsonTreeNode -> {
        when (element.value) {
          is JsonObject -> element.value.isNotEmpty()
          is JsonArray -> element.value.isNotEmpty()
          else -> false
        }
      }

      else -> false
    }
  }

  private fun getJsonElementChildren(node: JsonTreeNode): Array<Any> {
    return when (val element = node.value) {
      is JsonObject -> {
        element.entries.map { (key, value) ->
          JsonTreeNode(key, value, node)
        }.toTypedArray()
      }

      is JsonArray -> {
        element.mapIndexed { index, value ->
          JsonTreeNode("[$index]", value, node)
        }.toTypedArray()
      }

      else -> emptyArray()
    }
  }
}
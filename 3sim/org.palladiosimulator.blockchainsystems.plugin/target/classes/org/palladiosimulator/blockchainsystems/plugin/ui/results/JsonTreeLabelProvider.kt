package org.palladiosimulator.blockchainsystems.plugin.ui.results

import org.eclipse.jface.viewers.LabelProvider

/**
 * Label provider that provides text and images for JSON tree nodes in a tree viewer.
 */
open class JsonTreeLabelProvider : LabelProvider() {

  override fun getText(element: Any?): String {
    return when (element) {
      is JsonTreeNode -> element.getDisplayText()
      is String -> element
      else -> element?.toString() ?: ""
    }
  }

}

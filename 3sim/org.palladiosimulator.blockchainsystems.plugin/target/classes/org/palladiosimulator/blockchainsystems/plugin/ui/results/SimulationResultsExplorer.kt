package org.palladiosimulator.blockchainsystems.plugin.ui.results

import org.eclipse.jface.action.Action
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.DirectoryDialog
import org.eclipse.ui.part.ViewPart
import java.io.File

/**
 * This class provides a view for exploring simulation results.
 *
 * @author Davis Riedel
 */
abstract class SimulationResultsExplorer : ViewPart() {
  private val repositoryContainer = ResultsRepositoryContainer()
  private lateinit var viewer: TreeViewer

  /**
   * Content provider for the tree viewer.
   *
   * This extends the `JsonTreeContentProvider` to display the JSON contents of different simulation result files.
   */
  internal class DirectoryContentProvider : JsonTreeContentProvider() {
    override fun getElements(inputElement: Any?): Array<Any> {
      return when (inputElement) {
        is ResultsRepositoryContainer -> inputElement.resultRepositories.toTypedArray()
        else -> emptyArray()
      }
    }

    override fun getChildren(parentElement: Any?): Array<Any> {
      return when (parentElement) {
        is ResultsRepository -> parentElement.getSimulationResultFiles().toTypedArray()
        is SimulationResultFile -> super.getElements(parentElement.jsonContent)
        else -> super.getChildren(parentElement)
      }
    }

    override fun getParent(element: Any?): Any? {
      return when (element) {
        is ResultsRepositoryContainer -> null
        is SimulationResultFile -> element.repository
        is ResultsRepository -> element.container
        else -> super.getParent(element)
      }
    }

    override fun hasChildren(element: Any?): Boolean {
      return when (element) {
        is ResultsRepositoryContainer -> element.resultRepositories.isNotEmpty()
        is ResultsRepository -> element.getSimulationResultFiles().isNotEmpty()
        is SimulationResultFile -> super.hasChildren(element.jsonContent)
        else -> super.hasChildren(element)
      }
    }
  }

  internal class DirectoryLabelProvider : JsonTreeLabelProvider() {
    override fun getText(element: Any?): String {
      return when (element) {
        is ResultsRepositoryContainer -> "Repositories"
        is ResultsRepository -> element.directory?.let { it.name + " (" + it.path + ")" } ?: "Unknown Repository"
        is SimulationResultFile -> element.file?.name ?: "Unknown File"
        else -> super.getText(element)
      }
    }
  }

  override fun createPartControl(parent: Composite) {
    GridLayoutFactory
      .swtDefaults()
      .numColumns(1)
      .spacing(0, 0)
      .equalWidth(true)
      .applyTo(parent)

    // Create the tree viewer
    viewer = TreeViewer(parent, SWT.MULTI or SWT.H_SCROLL or SWT.V_SCROLL)
    GridDataFactory
      .swtDefaults()
      .grab(true, true)
      .align(SWT.FILL, SWT.FILL)
      .applyTo(viewer.tree)
    viewer.setContentProvider(DirectoryContentProvider())
    viewer.setLabelProvider(DirectoryLabelProvider())
    viewer.setInput(repositoryContainer)

    val toolBarManager = viewSite.actionBars.toolBarManager

    toolBarManager.add(loadRepositoryAction(parent))

    val refreshAction = object : Action() {
      override fun run() {
        viewer.refresh()
      }
    }
    refreshAction.setText("Refresh")
    refreshAction.setToolTipText("Refresh")
    toolBarManager.add(refreshAction)

    parent.layout()
  }

  private fun loadRepositoryAction(parent: Composite): Action {
    val action = object : Action() {
      override fun run() {
        val dialog = DirectoryDialog(parent.getShell(), SWT.OPEN)
        dialog.setText("Select a Directory")
        dialog.setFilterPath(System.getProperty("user.home"))
        dialog.open()?.let { directoryPath ->
          val directory = File(directoryPath)
          if (directory.isDirectory()) {
            repositoryContainer.addRepository(directory)
            viewer.refresh()
          }
        }
      }
    }
    action.setText("Load Repository")
    action.setToolTipText("Load a simulation results repository")
    return action
  }

  override fun setFocus() {
    viewer.control?.setFocus()
  }
}
package org.palladiosimulator.blockchainsystems.threesim_plugin

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup
import org.eclipse.debug.ui.CommonTab
import org.eclipse.debug.ui.ILaunchConfigurationDialog
import org.palladiosimulator.blockchainsystems.plugin.ui.tabs.*

class ThreesimTabGroup : AbstractLaunchConfigurationTabGroup() {
  override fun createTabs(arg0: ILaunchConfigurationDialog?, arg1: String?) {
    setTabs(
      ArchitecturalModelsTab(),
      SimulationTypeTab(),
      SimulationTerminationTab(),
      ThreesimTab(),
      LoggingTab(),
      CommonTab()
    )
  }
}
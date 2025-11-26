package org.palladiosimulator.blockchainsystems.threesim_plugin

import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab
import org.eclipse.jface.layout.GridLayoutFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Group
import org.palladiosimulator.blockchainsystems.plugin.ui.abstractions.TextField
import org.palladiosimulator.blockchainsystems.plugin.utils.DoubleVerifier
import org.palladiosimulator.blockchainsystems.plugin.utils.LongVerifier
import org.palladiosimulator.blockchainsystems.threesim_plugin.ThreesimAttributes

/**
 * Tab for configuring Threesim-specific simulation parameters.
 *
 * @author Davis Riedel
 */
class ThreesimTab : AbstractLaunchConfigurationTab() {
  companion object {
    private const val MIN_RELIABILITY_OBSERVATION_TIMESPAN = 0.0
    private const val MIN_NAKAMOTO_COEFFICIENT_THRESHOLD = 0.0
    private const val MAX_NAKAMOTO_COEFFICIENT_THRESHOLD = 100.0
    private const val MIN_SHANNON_ENTROPY_K = 0.0
    private const val MIN_FAILURE_THROUGHPUT_THRESHOLD = 0.0
  }

  private var isInitialized = false

  private lateinit var failureThroughputThresholdField: TextField
  private lateinit var shannonEntropyKField: TextField
  private lateinit var nakamotoCoefficientThresholdField: TextField
  private lateinit var reliabilityObservationTimespanField: TextField

  override fun createControl(parent: Composite) {
    val root = Composite(parent, SWT.BORDER)
    GridLayoutFactory.swtDefaults().numColumns(1).applyTo(root)

    val group = Group(root, SWT.NONE)
    group.text = "3SIM Parameters"
    GridLayoutFactory.swtDefaults().numColumns(3).spacing(0, 10).applyTo(group)
    group.layoutData = GridData(SWT.FILL, SWT.BEGINNING, true, false)

    failureThroughputThresholdField = TextField(
      group,
      "Failure Throughput Threshold:",
      " transactions/min",
      DoubleVerifier,
      ThreesimAttributes.FAILURE_THROUGHPUT_THRESHOLD,
      ThreesimAttributes.FAILURE_THROUGHPUT_THRESHOLD_DEFAULT,
      isValueValid = { it.toDoubleOrNull()?.let { it >= MIN_FAILURE_THROUGHPUT_THRESHOLD } ?: false }
    )

    shannonEntropyKField = TextField(
      group,
      "Shannon Entropy K:",
      "",
      DoubleVerifier,
      ThreesimAttributes.SHANNON_ENTROPY_K,
      ThreesimAttributes.SHANNON_ENTROPY_K_DEFAULT,
      isValueValid = { it.toDoubleOrNull()?.let { it > MIN_SHANNON_ENTROPY_K } ?: false }
    )

    nakamotoCoefficientThresholdField = TextField(
      group,
      "Nakamoto Coefficient Threshold:",
      " %",
      DoubleVerifier,
      ThreesimAttributes.NAKAMOTO_COEFFICIENT_THRESHOLD,
      ThreesimAttributes.NAKAMOTO_COEFFICIENT_THRESHOLD_DEFAULT,
      isValueValid = {
        it.toDoubleOrNull()?.let {
          it in MIN_NAKAMOTO_COEFFICIENT_THRESHOLD..MAX_NAKAMOTO_COEFFICIENT_THRESHOLD
        } ?: false
      }
    )

    reliabilityObservationTimespanField = TextField(
      group,
      "Reliability Observation Timespan:",
      " hours",
      DoubleVerifier,
      ThreesimAttributes.RELIABILITY_OBSERVATION_TIMESPAN,
      ThreesimAttributes.RELIABILITY_OBSERVATION_TIMESPAN_DEFAULT,
      isValueValid = { it.toDoubleOrNull()?.let { it > MIN_RELIABILITY_OBSERVATION_TIMESPAN } ?: false }
    )

    control = root
    isInitialized = true
  }

  override fun getName(): String {
    return "3SIM Parameters"
  }

  override fun initializeFrom(configuration: ILaunchConfiguration) {
    if (!isInitialized) return

    failureThroughputThresholdField.initializeFrom(configuration)
    shannonEntropyKField.initializeFrom(configuration)
    nakamotoCoefficientThresholdField.initializeFrom(configuration)
    reliabilityObservationTimespanField.initializeFrom(configuration)
  }

  override fun performApply(configuration: ILaunchConfigurationWorkingCopy) {
    if (!isInitialized) return

    failureThroughputThresholdField.performApply(configuration)
    shannonEntropyKField.performApply(configuration)
    nakamotoCoefficientThresholdField.performApply(configuration)
    reliabilityObservationTimespanField.performApply(configuration)
  }

  override fun setDefaults(configuration: ILaunchConfigurationWorkingCopy) {
    if (!isInitialized) return

    failureThroughputThresholdField.setDefaults(configuration)
    shannonEntropyKField.setDefaults(configuration)
    nakamotoCoefficientThresholdField.setDefaults(configuration)
    reliabilityObservationTimespanField.setDefaults(configuration)
  }

  override fun activated(workingCopy: ILaunchConfigurationWorkingCopy) {
    super.activated(workingCopy)
    updateLaunchConfigurationDialog()
  }

  override fun isValid(launchConfig: ILaunchConfiguration): Boolean {
    if (!isInitialized) return false

    return failureThroughputThresholdField.isValid() &&
      shannonEntropyKField.isValid() &&
      nakamotoCoefficientThresholdField.isValid() &&
      reliabilityObservationTimespanField.isValid()
  }
}
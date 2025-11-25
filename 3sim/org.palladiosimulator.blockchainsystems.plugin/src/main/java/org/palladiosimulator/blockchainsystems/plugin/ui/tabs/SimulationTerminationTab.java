package org.palladiosimulator.blockchainsystems.plugin.ui.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.blockchainsystems.plugin.Attributes;
import org.palladiosimulator.blockchainsystems.plugin.utils.ValidationUtils;

public class SimulationTerminationTab extends AbstractLaunchConfigurationTab {

    private final static int MINIMUM_ALLOWED_BLOCKCHAIN_LENGTH = 25;
    private final static int MAXIMUM_ALLOWED_BLOCKCHAIN_LENGTH = 10000;
    private final static String TAB_NAME = "Simulation Termination";

    private Text _maxBlockchainLengthText;
    private Label _maxBlockchainLengthLabel;
    private Text _requiredNumberOfBlocksInAdvanceText;

    @Override
    public void createControl(Composite parent) {

        Composite root = new Composite(parent, SWT.BORDER);
        setControl(root);

        GridLayoutFactory
                .swtDefaults()
                .numColumns(1)
                .applyTo(root);

        createMaxChainLengthInputGroup(root);
        createRequiredNumberOfBlocksInAdvanceLabel(root);
    }

    private boolean isMaxBlockchainLengthTextValid() {
        String text = _maxBlockchainLengthText.getText();
        // Check if the new text is a valid number
        return ValidationUtils.isStringPopulated(text)
                && ValidationUtils.isNumber(text)
                && ValidationUtils.isInRange(
                Integer.parseInt(text),
                MINIMUM_ALLOWED_BLOCKCHAIN_LENGTH,
                MAXIMUM_ALLOWED_BLOCKCHAIN_LENGTH);
    }

    private void createMaxChainLengthInputGroup(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Maximum Blockchain Length");
        group.setLayout(new GridLayout(1, false));
        group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
        // Create a text input
        _maxBlockchainLengthText = new Text(group, SWT.BORDER);
        _maxBlockchainLengthText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _maxBlockchainLengthText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                String oldText = ((Text) e.widget).getText();
                String newText = oldText.substring(0, e.start) + e.text + oldText.substring(e.end);

                if (!ValidationUtils.isNumber(newText)) {
                    e.doit = false;
                }
            }
        });
        _maxBlockchainLengthText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _maxBlockchainLengthLabel = new Label(group, SWT.NONE);
        _maxBlockchainLengthLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _maxBlockchainLengthLabel.setForeground(group.getDisplay().getSystemColor(SWT.COLOR_RED));
        _maxBlockchainLengthLabel.setText(
                "Maximum Blockchain Length must be bigger or equal to " + MINIMUM_ALLOWED_BLOCKCHAIN_LENGTH +
                        " and smaller or equal to " + MAXIMUM_ALLOWED_BLOCKCHAIN_LENGTH + ".");
        _maxBlockchainLengthLabel.setVisible(false);
    }

    private boolean isRequiredNumberOfBlocksInAdvanceTextValid() {
        String text = _requiredNumberOfBlocksInAdvanceText.getText();

        return ValidationUtils.isStringPopulated(text)
                && ValidationUtils.isNumber(text)
                && ValidationUtils.isInRange(
                Long.parseLong(text),
                1,
                Long.MAX_VALUE);
    }

    private void createRequiredNumberOfBlocksInAdvanceLabel(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Required Number of Blocks in Advance");
        group.setLayout(new GridLayout(1, false));
        group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

        _requiredNumberOfBlocksInAdvanceText = new Text(group, SWT.BORDER);
        _requiredNumberOfBlocksInAdvanceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _requiredNumberOfBlocksInAdvanceText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                String oldText = ((Text) e.widget).getText();
                String newText = oldText.substring(0, e.start) + e.text + oldText.substring(e.end);

                if (!ValidationUtils.isNumber(newText)) {
                    e.doit = false;
                }
            }
        });
        _requiredNumberOfBlocksInAdvanceText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            _maxBlockchainLengthText.setText(
                    configuration.getAttribute(
                            Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE,
                            Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE_DEFAULT));
            _requiredNumberOfBlocksInAdvanceText.setText(
                    configuration.getAttribute(
                            Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE,
                            Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE_DEFAULT));
        } catch (CoreException e) {
            _maxBlockchainLengthText.setText(
                    Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE_DEFAULT);
            _requiredNumberOfBlocksInAdvanceText.setText(
                    Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE_DEFAULT);
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE,
                _maxBlockchainLengthText.getText());
        configuration.setAttribute(
                Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE,
                _requiredNumberOfBlocksInAdvanceText.getText());
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE,
                Attributes.SimulationTermination.MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE,
                Attributes.SimulationTermination.REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE_DEFAULT);
    }

    @Override
    public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
        super.activated(workingCopy);
        updateLaunchConfigurationDialog();
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        boolean isMaxBlockchainLengthTextValid = isMaxBlockchainLengthTextValid();
        _maxBlockchainLengthLabel.setVisible(!isMaxBlockchainLengthTextValid);

        return isMaxBlockchainLengthTextValid
                && isRequiredNumberOfBlocksInAdvanceTextValid();
    }

    @Override
    public String getName() {
        return TAB_NAME;
    }
}

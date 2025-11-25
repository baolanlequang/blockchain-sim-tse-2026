package org.palladiosimulator.blockchainsystems.plugin.ui.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.blockchainsystems.plugin.Attributes;
import org.palladiosimulator.blockchainsystems.core.simulation.SimulationType;
import org.palladiosimulator.blockchainsystems.plugin.utils.ValidationUtils;

public class SimulationTypeTab extends AbstractLaunchConfigurationTab {

    private static final long MINIMUM_NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS = 1;

    private SimulationType _selectedSimulationType;

    private Button _selectSingleSimulationTypeRadioButton;
    private Button _selectMonteCarloSimulationTypeRadioButton;
    private Text _numberOfSimulationRoundsText;

    @Override
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.BORDER);
        setControl(root);

        GridLayoutFactory
                .swtDefaults()
                .numColumns(1)
                .applyTo(root);

        createSimulationTypeGroup(root);
    }

    private void createSimulationTypeGroup(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Simulation Type");
        GridLayoutFactory.swtDefaults().numColumns(1).spacing(0, 10).applyTo(group);
        group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

        _selectSingleSimulationTypeRadioButton = new Button(group, SWT.RADIO);
        _selectSingleSimulationTypeRadioButton.setText("Single Simulation");
        _selectSingleSimulationTypeRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        _selectSingleSimulationTypeRadioButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                onSimulationTypeSelected(SimulationType.Single);
                updateLaunchConfigurationDialog();
            }
        });

        _selectMonteCarloSimulationTypeRadioButton = new Button(group, SWT.RADIO);
        _selectMonteCarloSimulationTypeRadioButton.setText("Monte-Carlo Simulation");
        _selectMonteCarloSimulationTypeRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        _selectMonteCarloSimulationTypeRadioButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                onSimulationTypeSelected(SimulationType.MonteCarlo);
                updateLaunchConfigurationDialog();
            }
        });
        _numberOfSimulationRoundsText = new Text(group, SWT.BORDER);
        _numberOfSimulationRoundsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _numberOfSimulationRoundsText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                String oldText = ((Text) e.widget).getText();
                String newText = oldText.substring(0, e.start) + e.text + oldText.substring(e.end);

                if (!ValidationUtils.isNumber(newText)) {
                    e.doit = false;
                }
            }
        });
        _numberOfSimulationRoundsText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });
    }

    private void onSimulationTypeSelected(SimulationType simulationType) {
        _selectedSimulationType = simulationType;

        switch (simulationType) {
            case MonteCarlo:
                _selectSingleSimulationTypeRadioButton.setSelection(false);
                _selectMonteCarloSimulationTypeRadioButton.setSelection(true);
                _numberOfSimulationRoundsText.setEnabled(true);
                break;
            case Single:
                _selectSingleSimulationTypeRadioButton.setSelection(true);
                _selectMonteCarloSimulationTypeRadioButton.setSelection(false);
                _numberOfSimulationRoundsText.setEnabled(false);
                break;
            default:
                break;
        }
    }

    private boolean isNumberOfSimulationRoundsValid() {
        String text = _numberOfSimulationRoundsText.getText();

        return ValidationUtils.isStringPopulated(text)
                && ValidationUtils.isNumber(text)
                && ValidationUtils.isInRange(
                Long.parseLong(text),
                MINIMUM_NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS,
                Long.MAX_VALUE);
    }

    @Override
    public String getName() {
        return "Simulation Type";
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            String simulationTypeString = configuration.getAttribute(
                    Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE,
                    Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE_DEFAULT);

            onSimulationTypeSelected(
                    Enum.valueOf(
                            SimulationType.class,
                            simulationTypeString));

            _numberOfSimulationRoundsText.setText(
                    configuration.getAttribute(
                            Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS,
                            Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS_DEFAULT));

        } catch (CoreException | IllegalArgumentException | NullPointerException e) {
            onSimulationTypeSelected(
                    Enum.valueOf(
                            SimulationType.class,
                            Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE_DEFAULT));

            _numberOfSimulationRoundsText.setText(Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS_DEFAULT);
        }

    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE,
                _selectedSimulationType.toString());

        configuration.setAttribute(
                Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS,
                _numberOfSimulationRoundsText.getText());
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE,
                Attributes.SimulationType.SIMULATION_TYPE_ATTRIBUTE_DEFAULT);

        configuration.setAttribute(
                Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS,
                Attributes.SimulationType.NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS_DEFAULT);

    }

    @Override
    public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
        super.activated(workingCopy);
        updateLaunchConfigurationDialog();
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        if (_selectedSimulationType == SimulationType.MonteCarlo) {
            return isNumberOfSimulationRoundsValid();
        }

        return true;
    }
}

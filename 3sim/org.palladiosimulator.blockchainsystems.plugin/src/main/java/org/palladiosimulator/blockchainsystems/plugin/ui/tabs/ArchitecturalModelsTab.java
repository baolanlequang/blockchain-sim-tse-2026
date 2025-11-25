package org.palladiosimulator.blockchainsystems.plugin.ui.tabs;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.palladiosimulator.blockchainsystems.plugin.resourcefiles.*;
import org.palladiosimulator.blockchainsystems.plugin.utils.ValidationUtils;
import org.palladiosimulator.blockchainsystems.plugin.Attributes;

public class ArchitecturalModelsTab extends AbstractLaunchConfigurationTab {

    private static final String ECLIPSE_PLATFORM_URI_PREFIX = "platform:/resource";

    private Composite _parent;

    private Text _topologyFilePathText;
    private Button _blockchainSystemFileFromFilesystemSelectionButton;
    private Button _blockchainSystemFileFromWorkspaceSelectionButton;

    private Text _simulationResultsDirectoryPath;
    private Button _simulationResultsDirectoryPathFromFilesystemSelectionButton;

    @Override
    public void createControl(Composite parent) {
        _parent = parent;

        Composite root = new Composite(parent, SWT.BORDER);
        setControl(root);

        GridLayoutFactory
                .swtDefaults()
                .numColumns(1)
                .applyTo(root);

        createBlockchainSystemFileInputGroup(root);
    }

    private void createBlockchainSystemFileInputGroup(Composite parent) {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Blockchain System File");
        group.setLayout(new GridLayout(3, false));
        group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

        _topologyFilePathText = new Text(group, SWT.BORDER);
        _topologyFilePathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _topologyFilePathText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _blockchainSystemFileFromFilesystemSelectionButton = new Button(group, SWT.PUSH);
        _blockchainSystemFileFromFilesystemSelectionButton.setText("From Filesystem");
        _blockchainSystemFileFromFilesystemSelectionButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        _blockchainSystemFileFromFilesystemSelectionButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                FileDialog d = new FileDialog(_parent.getShell());
                String selectedDirectory = d.open();
                if (selectedDirectory != null) {
                    _topologyFilePathText.setText(selectedDirectory);
                } else {
                    _topologyFilePathText.setText("");
                }

                updateLaunchConfigurationDialog();
            }

        });


        _blockchainSystemFileFromWorkspaceSelectionButton = new Button(group, SWT.PUSH);
        _blockchainSystemFileFromWorkspaceSelectionButton.setText("From Workspace");
        _blockchainSystemFileFromWorkspaceSelectionButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        _blockchainSystemFileFromWorkspaceSelectionButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {


                ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
                        _parent.getShell(),
                        new WorkbenchLabelProvider(),
                        new BaseWorkbenchContentProvider()
                );

                // Set the title and message
                dialog.setTitle("Select Blockchain System Model File");
                dialog.setMessage("Select blockchain system from the workspace:");
                String[] fileExtensions = new String[] {"blockchainsystem"};
                dialog.addFilter(new FileExtensionFilter(fileExtensions));


                IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
                dialog.setInput(workspaceRoot);

                if (dialog.open() == Window.OK) {
                    Object[] result = dialog.getResult();
                    if (result != null && result.length > 0) {
                        IResource selectedResource = (IResource) result[0];

                        String platformPath = ECLIPSE_PLATFORM_URI_PREFIX + selectedResource.getFullPath();
                        _topologyFilePathText.setText(platformPath);
                    }
                }
                updateLaunchConfigurationDialog();
            }

        });


        Group simulationResultDirectoryGroup = new Group(parent, SWT.NONE);
        simulationResultDirectoryGroup.setText("Simulation Results Directory");
        simulationResultDirectoryGroup.setLayout(new GridLayout(3, false));
        simulationResultDirectoryGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

        _simulationResultsDirectoryPath = new Text(simulationResultDirectoryGroup, SWT.BORDER);
        _simulationResultsDirectoryPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _simulationResultsDirectoryPath.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _simulationResultsDirectoryPathFromFilesystemSelectionButton = new Button(simulationResultDirectoryGroup, SWT.PUSH);
        _simulationResultsDirectoryPathFromFilesystemSelectionButton.setText("From Filesystem");
        _simulationResultsDirectoryPathFromFilesystemSelectionButton.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false));
        _simulationResultsDirectoryPathFromFilesystemSelectionButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                DirectoryDialog d = new DirectoryDialog(_parent.getShell());
                String selectedDirectory = d.open();
                if (selectedDirectory != null) {
                    _simulationResultsDirectoryPath.setText(selectedDirectory);
                } else {
                    _simulationResultsDirectoryPath.setText("");
                }

                updateLaunchConfigurationDialog();
            }

        });
    }

    private boolean isTopologyFilePathTextValid() {
        String path = _topologyFilePathText.getText();

        return ValidationUtils.isStringPopulated(path);
    }

    private boolean isSimulationOutputDirectoryValid() {
        String path = _simulationResultsDirectoryPath.getText();

        return ValidationUtils.isStringPopulated(path)
                && Files.exists(Path.of(path));
    }

    @Override
    public String getName() {
        return "Architectural Models";
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            _topologyFilePathText.setText(
                    configuration.getAttribute(
                            Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE,
                            Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE_DEFAULT));
            _simulationResultsDirectoryPath.setText(
                    configuration.getAttribute(
                            Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY,
                            Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY_DEFAULT));
        } catch (CoreException e) {
            _topologyFilePathText.setText(
                    Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE_DEFAULT);
            _simulationResultsDirectoryPath.setText(
                    Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY_DEFAULT);
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE,
                _topologyFilePathText.getText());
        configuration.setAttribute(
                Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY,
                _simulationResultsDirectoryPath.getText());
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE,
                Attributes.ArchitecturalModels.BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY,
                Attributes.ArchitecturalModels.SIMULATION_RESULT_FILE_DIRECTORY_DEFAULT);
    }

    @Override
    public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
        super.activated(workingCopy);
        updateLaunchConfigurationDialog();
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        return isTopologyFilePathTextValid()
                && isSimulationOutputDirectoryValid();
    }
}

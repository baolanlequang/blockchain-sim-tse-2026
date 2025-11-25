package org.palladiosimulator.blockchainsystems.plugin.ui.tabs;

import java.nio.file.Files;
import java.nio.file.Path;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.blockchainsystems.plugin.Attributes;
import org.palladiosimulator.blockchainsystems.plugin.utils.ValidationUtils;

public class LoggingTab extends AbstractLaunchConfigurationTab {

    private final static String TAB_NAME = "Logging";

    private Composite _parent;
    private Button _enableConsoleLoggingCheckButton;
    private Button _enableFileLoggingCheckButton;
    private Text _logFilePathSelectionText;
    private Button _logFilePathSelectionButton;
    private Button _enableDatabaseLoggingCheckButton;

    private Label _databaseServerLabel;
    private Text _databaseServerText;
    private Label _databaseNameLabel;
    private Text _databaseNameText;
    private Label _databasePortLabel;
    private Text _databasePortText;
    private Label _databaseUsernameLabel;
    private Text _databaseUsernameText;
    private Label _databasePasswordLabel;
    private Text _databasePasswordText;

    @Override
    public void createControl(Composite parent) {
        _parent = parent;

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));
        setControl(container);

        Group loggingGroup = new Group(container, SWT.NONE);
        loggingGroup.setText("Loggers");
        loggingGroup.setLayout(new GridLayout(1, false));
        loggingGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        createConsoleLoggingCheckButton(loggingGroup);
        createFileLoggingCheckButton(loggingGroup);
        createDatabaseLoggingCheckButton(loggingGroup);
    }

    private void createConsoleLoggingCheckButton(Group loggingGroup) {
        _enableConsoleLoggingCheckButton = new Button(loggingGroup, SWT.CHECK);
        _enableConsoleLoggingCheckButton.setText("Console Logging");
        _enableConsoleLoggingCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateLaunchConfigurationDialog();
            }
        });
    }

    private void createFileLoggingCheckButton(Group loggingGroup) {
        _enableFileLoggingCheckButton = new Button(loggingGroup, SWT.CHECK);
        _enableFileLoggingCheckButton.setText("File Logging");
        _enableFileLoggingCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setLogFilePathSelectionEnabled(_enableFileLoggingCheckButton.getSelection());
                updateLaunchConfigurationDialog();
            }
        });

        Composite logFilePathSelectionContainer = new Composite(loggingGroup, SWT.NONE);
        logFilePathSelectionContainer.setLayout(new GridLayout(2, false));
        logFilePathSelectionContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        _logFilePathSelectionText = new Text(logFilePathSelectionContainer, SWT.BORDER);
        _logFilePathSelectionText.setText("");
        _logFilePathSelectionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _logFilePathSelectionText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _logFilePathSelectionButton = new Button(logFilePathSelectionContainer, SWT.PUSH);
        _logFilePathSelectionButton.setText("From Filesystem");
        _logFilePathSelectionButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        _logFilePathSelectionButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                DirectoryDialog d = new DirectoryDialog(_parent.getShell());
                String selectedDirectory = d.open();
                if (selectedDirectory != null) {
                    _logFilePathSelectionText.setText(selectedDirectory);
                } else {
                    _logFilePathSelectionText.setText("");
                }

                updateLaunchConfigurationDialog();
            }

        });
    }

    private void setLogFilePathSelectionEnabled(boolean enabled) {
        _logFilePathSelectionText.setEnabled(enabled);
        _logFilePathSelectionButton.setEnabled(enabled);
    }

    private void createDatabaseLoggingCheckButton(Group loggingGroup) {
        _enableDatabaseLoggingCheckButton = new Button(loggingGroup, SWT.CHECK);
        _enableDatabaseLoggingCheckButton.setText("Database Logging");
        _enableDatabaseLoggingCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setDatabaseLoggingEnabled(_enableDatabaseLoggingCheckButton.getSelection());
                updateLaunchConfigurationDialog();
            }
        });

        Composite databaseConnectionInfoContainer = new Composite(loggingGroup, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(4).equalWidth(false).spacing(20, 10).applyTo(databaseConnectionInfoContainer);
        databaseConnectionInfoContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        _databaseServerLabel = new Label(databaseConnectionInfoContainer, SWT.NONE);
        _databaseServerLabel.setText("Server:");
        _databaseServerLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        _databaseServerText = new Text(databaseConnectionInfoContainer, SWT.NONE);
        _databaseServerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _databaseServerText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _databaseNameLabel = new Label(databaseConnectionInfoContainer, SWT.NONE);
        _databaseNameLabel.setText("Database:");
        _databaseNameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        _databaseNameText = new Text(databaseConnectionInfoContainer, SWT.NONE);
        _databaseNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _databaseNameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _databasePortLabel = new Label(databaseConnectionInfoContainer, SWT.NONE);
        _databasePortLabel.setText("Port:");
        _databasePortLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        _databasePortText = new Text(databaseConnectionInfoContainer, SWT.NONE);
        _databasePortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _databasePortText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });
        _databasePortText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                String oldText = ((Text) e.widget).getText();
                String newText = oldText.substring(0, e.start) + e.text + oldText.substring(e.end);

                if (!ValidationUtils.isNumber(newText)) {
                    e.doit = false;
                }
            }
        });

        Label placeHoderLabel1 = new Label(databaseConnectionInfoContainer, SWT.NONE);
        placeHoderLabel1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        Label placeHoderLabel2 = new Label(databaseConnectionInfoContainer, SWT.NONE);
        placeHoderLabel2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

        _databaseUsernameLabel = new Label(databaseConnectionInfoContainer, SWT.NONE);
        _databaseUsernameLabel.setText("Username:");
        _databaseUsernameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        _databaseUsernameText = new Text(databaseConnectionInfoContainer, SWT.NONE);
        _databaseUsernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _databaseUsernameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });

        _databasePasswordLabel = new Label(databaseConnectionInfoContainer, SWT.NONE);
        _databasePasswordLabel.setText("Password:");
        _databasePasswordLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        _databasePasswordText = new Text(databaseConnectionInfoContainer, SWT.NONE);
        _databasePasswordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        _databasePasswordText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateLaunchConfigurationDialog();
            }
        });
    }

    private void setDatabaseLoggingEnabled(boolean enabled) {
        _databaseServerText.setEnabled(enabled);
        _databaseNameText.setEnabled(enabled);
        _databasePortText.setEnabled(enabled);
        _databaseUsernameText.setEnabled(enabled);
        _databasePasswordText.setEnabled(enabled);
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            _enableConsoleLoggingCheckButton.setSelection(
                    configuration.getAttribute(
                            Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE,
                            Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT));

            _enableFileLoggingCheckButton.setSelection(
                    configuration.getAttribute(
                            Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE,
                            Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT));
            _logFilePathSelectionText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE,
                            Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE_DEFAULT));

            _enableDatabaseLoggingCheckButton.setSelection(
                    configuration.getAttribute(
                            Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE,
                            Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT));
            _databaseServerText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.DATABASE_SERVER_ATTRIBUTE,
                            Attributes.Logging.DATABASE_SERVER_ATTRIBUTE_DEFAULT));
            _databaseNameText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.DATABASE_NAME_ATTRIBUTE,
                            Attributes.Logging.DATABASE_NAME_ATTRIBUTE_DEFAULT));
            _databasePortText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.DATABASE_PORT_ATTRIBUTE,
                            Attributes.Logging.DATABASE_PORT_ATTRIBUTE_DEFAULT));
            _databaseUsernameText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE,
                            Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE_DEFAULT));
            _databasePasswordText.setText(
                    configuration.getAttribute(
                            Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE,
                            Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE_DEFAULT));
        } catch (CoreException e) {
            _enableConsoleLoggingCheckButton.setSelection(
                    Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);

            _enableFileLoggingCheckButton.setSelection(
                    Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);
            _logFilePathSelectionText.setText(
                    Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE_DEFAULT);

            _enableDatabaseLoggingCheckButton.setSelection(
                    Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);
            _databaseServerText.setText(
                    Attributes.Logging.DATABASE_SERVER_ATTRIBUTE_DEFAULT);
            _databaseNameText.setText(
                    Attributes.Logging.DATABASE_NAME_ATTRIBUTE_DEFAULT);
            _databasePortText.setText(
                    Attributes.Logging.DATABASE_PORT_ATTRIBUTE_DEFAULT);
            _databaseUsernameText.setText(
                    Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE_DEFAULT);
            _databasePasswordText.setText(
                    Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE_DEFAULT);
        }

        setLogFilePathSelectionEnabled(_enableFileLoggingCheckButton.getSelection());
        setDatabaseLoggingEnabled(_enableDatabaseLoggingCheckButton.getSelection());
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE,
                _enableConsoleLoggingCheckButton.getSelection());
        configuration.setAttribute(
                Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE,
                _enableFileLoggingCheckButton.getSelection());
        configuration.setAttribute(
                Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE,
                _logFilePathSelectionText.getText());
        configuration.setAttribute(
                Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE,
                _enableDatabaseLoggingCheckButton.getSelection());

        configuration.setAttribute(
                Attributes.Logging.DATABASE_SERVER_ATTRIBUTE,
                _databaseServerText.getText());
        configuration.setAttribute(
                Attributes.Logging.DATABASE_NAME_ATTRIBUTE,
                _databaseNameText.getText());
        configuration.setAttribute(
                Attributes.Logging.DATABASE_PORT_ATTRIBUTE,
                _databasePortText.getText());
        configuration.setAttribute(
                Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE,
                _databaseUsernameText.getText());
        configuration.setAttribute(
                Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE,
                _databasePasswordText.getText());
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(
                Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE,
                Attributes.Logging.IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE,
                Attributes.Logging.IS_FILE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE,
                Attributes.Logging.LOG_FILE_PATH_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE,
                Attributes.Logging.IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT);

        configuration.setAttribute(
                Attributes.Logging.DATABASE_SERVER_ATTRIBUTE,
                Attributes.Logging.DATABASE_SERVER_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.DATABASE_NAME_ATTRIBUTE,
                Attributes.Logging.DATABASE_NAME_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.DATABASE_PORT_ATTRIBUTE,
                Attributes.Logging.DATABASE_PORT_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE,
                Attributes.Logging.DATABASE_USERNAME_ATTRIBUTE_DEFAULT);
        configuration.setAttribute(
                Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE,
                Attributes.Logging.DATABASE_PASSWORD_ATTRIBUTE_DEFAULT);
    }

    private boolean isSelectedLogFilePathValid() {
        if (!_enableFileLoggingCheckButton.getSelection()) {
            return true;
        }

        String selectedPath = _logFilePathSelectionText.getText();

        return ValidationUtils.isStringPopulated(selectedPath)
                && Files.exists(Path.of(selectedPath));
    }

    private boolean areDatabaseInformationValid() {
        if (!_enableDatabaseLoggingCheckButton.getSelection()) {
            return true;
        }

        return ValidationUtils.isStringPopulated(_databaseServerText.getText())
                && ValidationUtils.isStringPopulated(_databaseNameText.getText())
                && ValidationUtils.isPort(_databasePortText.getText())
                && ValidationUtils.isStringPopulated(_databaseUsernameText.getText())
                && ValidationUtils.isStringPopulated(_databasePasswordText.getText());
    }

    @Override
    public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
        super.activated(workingCopy);
        updateLaunchConfigurationDialog();
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        return isSelectedLogFilePathValid()
                && areDatabaseInformationValid();
    }

    @Override
    public String getName() {
        return TAB_NAME;
    }
}

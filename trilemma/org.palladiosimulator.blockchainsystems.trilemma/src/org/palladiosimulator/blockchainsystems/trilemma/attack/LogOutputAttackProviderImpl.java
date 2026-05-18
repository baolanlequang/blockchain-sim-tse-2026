package org.palladiosimulator.blockchainsystems.trilemma.attack;

import java.util.HashSet;
import java.util.Set;

import org.palladiosimulator.blockchainsystems.core.tracing.TraceEventLogOutput;
import org.palladiosimulator.blockchainsystems.core.simulation.logoutputs.abstractions.LogOutputProvider;
import org.palladiosimulator.blockchainsystems.loggers.TraceEventConsoleLogger;

import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonKt;

import org.palladiosimulator.blockchainsystems.loggers.*;

public class LogOutputAttackProviderImpl implements LogOutputProvider {

	private final boolean _useConsoleLogging;
	
	private final boolean _useFileLogging;
	private final String _fileLoggingDirectoryPath;
	
	private final boolean _useDatabaseLogging;
	private final String _dbServer;
	private final String _dbName;
	private final int _dbPort;
	private final String _dbUsername;
	private final String _dbPassword;
	private final int _runId;
	
	public LogOutputAttackProviderImpl(
			boolean useConsoleLogging,
			boolean useFileLogging,
			String fileLoggingDirectoryPath,
			boolean useDatabaseLogging,
			String dbServer,
			int dbPort,
			String dbName,
			String dbUsername,
			String dbPassword,
			int runId) {
		_useConsoleLogging = useConsoleLogging;
		_useFileLogging = useFileLogging;
		_fileLoggingDirectoryPath = fileLoggingDirectoryPath;
		_useDatabaseLogging = useDatabaseLogging;
		_dbServer = dbServer;
		_dbName = dbName;
		_dbPort = dbPort;
		_dbUsername = dbUsername;
		_dbPassword = dbPassword;
		_runId = runId;
	}


	@Override
	public Set<TraceEventLogOutput> getLogOutputs() {
		HashSet<TraceEventLogOutput> logOutputs = new HashSet<TraceEventLogOutput>();
		
		if (_useConsoleLogging) {
			logOutputs.add(createConsoleLogger());
		}
		
		if (_useFileLogging) {
			logOutputs.add(createFileLogger());
		}
		
		if (_useDatabaseLogging) {
			logOutputs.add(createDatabaseLogger());
		}
		
		return logOutputs;
	}

	private TraceEventLogOutput createConsoleLogger() {
//		Json json = Json.Default;
//		return new TraceEventConsoleLogger(json);
		
		return new NormalTraceEventLogger();
	}
	
	private TraceEventLogOutput createFileLogger() {
		Json json = Json.Default;
		return new TraceEventFileLogger(json, _fileLoggingDirectoryPath);
	}
	
	private TraceEventLogOutput createDatabaseLogger() {
		return null;
	}

}

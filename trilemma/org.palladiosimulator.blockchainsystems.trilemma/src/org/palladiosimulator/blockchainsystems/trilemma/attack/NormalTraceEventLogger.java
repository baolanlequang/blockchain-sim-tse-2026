package org.palladiosimulator.blockchainsystems.trilemma.attack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEvent;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.TraceEventLogOrigin;
import org.palladiosimulator.blockchainsystems.core.tracing.*;
import org.palladiosimulator.blockchainsystems.doublespending.behavior.MaliciousTraceEvent;

public class NormalTraceEventLogger implements TraceEventLogOutput {
	
	private int _runId;
	
	NormalTraceEventLogger(int runId) {
		_runId = runId;
	}

	@Override
	public void onTraceEventOccurred(TraceEvent event, TraceEventLogOrigin origin) {
		if (event instanceof MaliciousTraceEvent maliciousTraceEvent) {
//			System.out.println(maliciousTraceEvent.resourcePower()); 
			try {
	            // Create directory if it does not exist
	            File directory = new File("selfish_log");
	            if (!directory.exists()) {
	                directory.mkdirs();
	            }

	            // Write to file
	            String fileName = String.format("selfish_log/log_for_run_%d.txt", _runId);
	            try (PrintWriter writer =
	                     new PrintWriter(new FileWriter(fileName, true))) {

	                writer.println("ResourcePower: " + maliciousTraceEvent.resourcePower());
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

}

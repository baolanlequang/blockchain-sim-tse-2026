package org.palladiosimulator.blockchainsystems.trilemma;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.mdsd.library.standalone.initialization.StandaloneInitializationException;
import tools.mdsd.library.standalone.initialization.StandaloneInitializerBuilder;

public class BlockchainTrilemmaStandalone {
	private final Logger logger = Logger.getLogger(BlockchainTrilemmaStandalone.class);
	
	private String modelProjectName = "";
    private Class<? extends Plugin> modelProjectActivator;
	
	public BlockchainTrilemmaStandalone(String modelProjectName,
            Class<? extends Plugin> modelProjectActivator) {
		this.modelProjectName = modelProjectName;
        this.modelProjectActivator = modelProjectActivator;
	}
	
	public boolean initAnalysis() {
		EcorePlugin.ExtensionProcessor.process(null);
		if (!initStandalone()) {
            return false;
		}
		return true;
	}
	
	private boolean initStandalone() {
        try {
            StandaloneInitializerBuilder.builder()
                .registerProjectURI(this.modelProjectActivator, this.modelProjectName)
                .build()
                .init();

            logger.info("Successfully initialized standalone environment.");
            return true;

        } catch (StandaloneInitializationException e) {
            logger.error("Unable to initialize standalone environment.");
            e.printStackTrace();
            return false;
        }
    }
}

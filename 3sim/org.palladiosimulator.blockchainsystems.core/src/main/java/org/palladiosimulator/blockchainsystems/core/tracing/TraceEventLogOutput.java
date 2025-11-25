package org.palladiosimulator.blockchainsystems.core.tracing;

public interface TraceEventLogOutput extends TraceEventSubscriber {
    void initialize();

    void cleanUp();
}

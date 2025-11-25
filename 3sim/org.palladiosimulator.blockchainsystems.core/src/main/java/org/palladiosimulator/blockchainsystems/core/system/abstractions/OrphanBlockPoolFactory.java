package org.palladiosimulator.blockchainsystems.core.system.abstractions;

/**
 * Interface for a factory that produces instances of {@code OrphanBlockPool}.
 * 
 * @author Yannik Sproll
 *
 */
public interface OrphanBlockPoolFactory {

	/**
	 * Creates an instance of @code{OrphanBlockPool} for a specified blockchain system node.
	 * 
	 * @param nodeId the id of the node the created {@code OrphanBlockPool} belongs to
	 * @return a @code{OrphanBlockPool} instance
	 */
	OrphanBlockPool createOrphanBlockPool(String nodeId);
}

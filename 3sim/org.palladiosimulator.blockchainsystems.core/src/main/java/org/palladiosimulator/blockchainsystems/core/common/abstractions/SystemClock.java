package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * 
 * The @code{SystemClock} interface represents a global simulation clock.
 * 
 * @author Yannik Sproll
 *
 */
public interface SystemClock {

	/**
	 * Returns the current simulation time.
	 * 
	 * @return the current simulation time
	 */
	long getCurrentTime();
}

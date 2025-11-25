package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * 
 * The {@code Taggable} interface represents a class describing that can have annotated tags.
 * It provides a method to check if the object has a specified tag.
 * 
 * @author Yannik Sproll
 *
 */
public interface Taggable {

	/**
	 * Checks if the target has the specified tag.
	 * 
	 * @param tag the tag to check
	 * @return true if the target has the tag, false otherwise
	 */
	boolean hasTag(String tag);
}

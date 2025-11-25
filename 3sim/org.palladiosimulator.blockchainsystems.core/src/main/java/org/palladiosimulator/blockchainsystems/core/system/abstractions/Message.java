package org.palladiosimulator.blockchainsystems.core.system.abstractions;

/**
 * The @code{Message} interface represents a message that can be sent
 * by a blockchain system node over its P2P network interface.
 * 
 * @author Yannik Sproll
 *
 */
public interface Message {

	/**
	 * Returns the content of the message.
	 * 
	 * @return content of the message
	 */
	public Object getContent();
	
	/**
	 * Returns the content type of the message.
	 * 
	 * @return content type of the message
	 */
	public String getContentType();
	
	/**
	 * Returns the byte size of the message.
	 * @return byte size of the message
	 */
	public int getSize();
}

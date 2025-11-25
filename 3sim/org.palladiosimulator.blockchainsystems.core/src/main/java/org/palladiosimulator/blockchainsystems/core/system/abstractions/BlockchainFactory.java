package org.palladiosimulator.blockchainsystems.core.system.abstractions;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;

/**
 * Interface for a factory that produces instances of {@code Blockchain}.
 *
 * @author Yannik Sproll
 */
public interface BlockchainFactory {

    /**
     * Creates an instance of {@code Blockchain} for a specified blockchain system node.
     *
     * @param genesisBlock the genesis block of the blockchain
     * @param nodeId       the id of the node the created {@code Blockchain} belongs to
     * @return a {@code Blockchain} instance
     */
    Blockchain createBlockchain(Block genesisBlock, String nodeId);
}

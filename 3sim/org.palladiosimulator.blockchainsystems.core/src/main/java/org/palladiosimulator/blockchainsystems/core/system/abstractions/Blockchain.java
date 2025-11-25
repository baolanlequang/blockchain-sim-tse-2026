package org.palladiosimulator.blockchainsystems.core.system.abstractions;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockAppendingResult;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable;

/**
 * The {@code Blockchain} interface represents
 * the blockchain data structure of a blockchain system node.
 *
 * @author Yannik Sproll
 */
public interface Blockchain extends ReadonlyBlockchain, Traceable {

    /**
     * Appends the specified block to the blockchain.
     * It returns a @code{BlockAppendingResult} that describes how and if the block was added to the blockchain.
     *
     * @param block the block to append to the blockchain
     * @return the @code{BlockAppendingResult} that describes the result of the append operation
     */
    BlockAppendingResult appendBlock(Block block);
}

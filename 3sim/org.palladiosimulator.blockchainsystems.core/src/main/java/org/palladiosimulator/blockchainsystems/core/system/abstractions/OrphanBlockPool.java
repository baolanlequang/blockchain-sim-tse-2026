package org.palladiosimulator.blockchainsystems.core.system.abstractions;

import java.util.Set;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Traceable;

/**
 * The @code{OrphanBlockPool} interface is an abstraction of an orphan block pool,
 * that is used by blockchain system nodes to store orphan blocks.
 *
 * @author Yannik Sproll
 */
public interface OrphanBlockPool extends Traceable {

    /**
     * Returns each stored block that's previous hash matches the specified hash.
     * If there is no matching block this method returns an empty set.
     *
     * @param previousBlockHash the hash of the previous block
     * @return the requested blocks
     */
    Set<Block> getBlocksByPreviousBlockHash(String previousBlockHash);

    /**
     * Stores the specified block in the orphan block pool.
     *
     * @param block the block to store in the orphan block pool
     */
    void storeBlock(Block block);
}

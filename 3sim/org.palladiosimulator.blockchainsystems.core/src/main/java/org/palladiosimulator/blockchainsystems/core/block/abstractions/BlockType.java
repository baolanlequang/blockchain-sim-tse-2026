package org.palladiosimulator.blockchainsystems.core.block.abstractions;

/**
 * The @code{BlockType} enum specifies possible block types as which blocks can
 * be added to a blockchain data structure.
 *
 * @author Yannik Sproll, Davis Riedel
 */
public enum BlockType {
    /**
     * The block is included in the main chain, but has not enough successors to be considered confirmed
     */
    IncludedBlock,
    /**
     * The block is included in the main chain and has enough successors to be considered confirmed
     */
    ConfirmedBlock,
    /**
     * The block is appended, but is  stale
     */
    StaleBlock,
    /**
     * The block is appended, but is part of a forked branch of the chain
     */
    ForkingBlock
}

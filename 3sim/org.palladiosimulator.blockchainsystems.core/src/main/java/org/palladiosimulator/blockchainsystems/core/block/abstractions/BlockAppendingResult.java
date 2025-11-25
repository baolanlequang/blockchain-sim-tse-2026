package org.palladiosimulator.blockchainsystems.core.block.abstractions;

/**
 * The @code{BlockAppendingResult} class describes
 * the result of a block appending operation on a blockchain.
 *
 * @author Yannik Sproll
 */
public class BlockAppendingResult {
    private final BlockAppendingResultType _type;
    private final BlockType _blockType;

    private BlockAppendingResult(BlockAppendingResultType type, BlockType blockType) {
        _type = type;
        _blockType = blockType;
    }

    /**
     * @return the type of the block appending result
     */
    public BlockAppendingResultType getType() {
        return _type;
    }

    /**
     * @return the block type of the appended block
     */
    public BlockType getBlockType() {
        return _blockType;
    }

    /**
     * @param blockType the type of the block to append
     * @return a new block appending result with the type @code{Appended}
     */
    public static BlockAppendingResult createBlockAppendedResult(BlockType blockType) {
        return new BlockAppendingResult(BlockAppendingResultType.Appended, blockType);
    }

    /**
     * @return a new block appending result with the type @code{AlreadyAppended}
     */
    public static BlockAppendingResult createBlockAlreadyAppendedResult() {
        return new BlockAppendingResult(BlockAppendingResultType.AlreadyAppended, null);
    }

    /**
     * @return a new block appending result with the type @code{NotAppendedBecauseOrphanBlock}
     */
    public static BlockAppendingResult createBlockNoAppendedBecauseOrphanBlockResult() {
        return new BlockAppendingResult(BlockAppendingResultType.NotAppendedBecauseOrphanBlock, null);
    }
}

package org.palladiosimulator.blockchainsystems.core.block.abstractions;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The @code{BlockAppendingResult} class describes
 * the result of a block appending operation on a blockchain.
 *
 * In addition to the type of the newly appended block, the result records blocks
 * whose local-chain classification changed as a consequence of resolving a fork.
 * This is required for correct transaction-mempool reconciliation: transactions
 * from a branch that becomes stale must become eligible again unless the same
 * transaction is present on the winning branch.
 *
 * @author Yannik Sproll
 */
public final class BlockAppendingResult {
    private final BlockAppendingResultType _type;
    private final BlockType _blockType;
    private final Set<Block> _blocksBecameStale;
    private final Set<Block> _blocksBecameIncluded;

    private BlockAppendingResult(
            BlockAppendingResultType type,
            BlockType blockType,
            Set<Block> blocksBecameStale,
            Set<Block> blocksBecameIncluded) {
        _type = type;
        _blockType = blockType;
        _blocksBecameStale = Collections.unmodifiableSet(new LinkedHashSet<>(blocksBecameStale));
        _blocksBecameIncluded = Collections.unmodifiableSet(new LinkedHashSet<>(blocksBecameIncluded));
    }

    public BlockAppendingResultType getType() {
        return _type;
    }

    public BlockType getBlockType() {
        return _blockType;
    }

    /** Blocks that changed from a competing longest branch to stale. */
    public Set<Block> getBlocksBecameStale() {
        return _blocksBecameStale;
    }

    /** Blocks that changed from forked to included, plus a newly included block. */
    public Set<Block> getBlocksBecameIncluded() {
        return _blocksBecameIncluded;
    }

    public static BlockAppendingResult createBlockAppendedResult(BlockType blockType) {
        return createBlockAppendedResult(blockType, Collections.emptySet(), Collections.emptySet());
    }

    public static BlockAppendingResult createBlockAppendedResult(
            BlockType blockType,
            Set<Block> blocksBecameStale,
            Set<Block> blocksBecameIncluded) {
        return new BlockAppendingResult(
                BlockAppendingResultType.Appended,
                blockType,
                blocksBecameStale,
                blocksBecameIncluded);
    }

    public static BlockAppendingResult createBlockAlreadyAppendedResult() {
        return new BlockAppendingResult(
                BlockAppendingResultType.AlreadyAppended,
                null,
                Collections.emptySet(),
                Collections.emptySet());
    }

    public static BlockAppendingResult createBlockNoAppendedBecauseOrphanBlockResult() {
        return new BlockAppendingResult(
                BlockAppendingResultType.NotAppendedBecauseOrphanBlock,
                null,
                Collections.emptySet(),
                Collections.emptySet());
    }
}

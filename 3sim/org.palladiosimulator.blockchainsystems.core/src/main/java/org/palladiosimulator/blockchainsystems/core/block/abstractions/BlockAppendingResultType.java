package org.palladiosimulator.blockchainsystems.core.block.abstractions;

/**
 * The @code{BlockAppendingResultType} enum provides the values
 * for the different types of @code{BlockAppendingResult}.
 *
 * @author Yannik Sproll
 */
public enum BlockAppendingResultType {
    Appended,
    NotAppendedBecauseOrphanBlock,
    AlreadyAppended;
}

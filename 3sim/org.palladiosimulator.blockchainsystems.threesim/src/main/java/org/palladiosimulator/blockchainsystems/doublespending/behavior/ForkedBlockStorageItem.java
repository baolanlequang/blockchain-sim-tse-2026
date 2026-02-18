package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;

public record ForkedBlockStorageItem(
		int position,
		Block block) {
}

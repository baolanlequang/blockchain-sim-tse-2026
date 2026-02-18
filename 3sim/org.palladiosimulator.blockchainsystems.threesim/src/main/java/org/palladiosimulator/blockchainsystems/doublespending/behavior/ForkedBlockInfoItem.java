package org.palladiosimulator.blockchainsystems.doublespending.behavior;

public record ForkedBlockInfoItem(
		String attackOriginBlockHash,
		int attackOriginBlockOffset) {
}

package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;

public class BlockToOverrideStorageItem {
	
	private static final Comparator<Block> BLOCK_COMPARATOR = (b1, b2) -> Long.compare(b1.getBlockMinedTimestamp(), b2.getBlockMinedTimestamp());

	private final String _attackOriginBlockHash;
	private final TreeSet<Block> _blocksToOverride;
	
	public BlockToOverrideStorageItem(String attackOriginBlockHash) {
		_attackOriginBlockHash = attackOriginBlockHash;
		_blocksToOverride = new TreeSet<Block>(BLOCK_COMPARATOR);
	}
	
	public Set<Block> getBlocks() {
		return Collections.unmodifiableSet(_blocksToOverride);
	}
	
	public void addBlock(Block block) {
		if (block.getPreviousHash() != _attackOriginBlockHash) {
			return;
		}
		
		_blocksToOverride.add(block);
	}
	
	public Block getFirstMinedBlock() {
		return _blocksToOverride
				.stream()
				.findFirst()
				.orElse(null);
	}
	
	public String getAttackOriginBlockHash() {
		return _attackOriginBlockHash;
	}
}

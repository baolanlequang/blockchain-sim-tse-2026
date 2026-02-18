package org.palladiosimulator.blockchainsystems.doublespending.simulation.termination;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.BlockType;

public class BlockState {

	private final Block _block;
	private final long _blockPosition;
	private BlockType _blockType;
	
	public BlockState(Block block, long blockPosition, BlockType blockType) {
		_block = block;
		_blockPosition = blockPosition;
		_blockType = blockType;
	}
	
	public Block getBlock() {
		return _block;
	}
	
	public long getBlockPosition() {
		return _blockPosition;
	}
	
	public BlockType getBlockType() {
		return _blockType;
	}
	
	public void setBlockType(BlockType newBlockType) {
		_blockType = newBlockType;
	}
}

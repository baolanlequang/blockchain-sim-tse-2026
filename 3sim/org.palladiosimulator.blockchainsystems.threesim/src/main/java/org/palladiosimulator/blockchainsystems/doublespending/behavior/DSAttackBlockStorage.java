package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.HashMap;
import java.util.Map.Entry;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;

/**
 * 
 * The {@code DSAttackBlockStorage} keeps track of the malicious blocks used
 * by a malicious node during a double spending attack.
 * Provides methods to get the attack origin block used for the double spending attack.
 * 
 * @author Yannik Sproll
 *
 */
public class DSAttackBlockStorage {

	private final HashMap<String, BlockToOverrideStorageItem> _blockToOverrideStorageItems;
	private final HashMap<String, ForkedBlockInfoItem> _forkedBlockInfoByForkedBlockHashes;
	
	
	public DSAttackBlockStorage() {
		_blockToOverrideStorageItems = new HashMap<String, BlockToOverrideStorageItem>();
		_forkedBlockInfoByForkedBlockHashes = new HashMap<String, ForkedBlockInfoItem>();
	}
	
	public String getHashOfLatestForkedBlock() {
		if (_blockToOverrideStorageItems.isEmpty()) {
			return null;
		}
		
		String attackOriginBlockHash = getHashOfFirstMinedAttackOriginBlock();
		
		String hashOfLatestForkedBlock = _forkedBlockInfoByForkedBlockHashes
			.entrySet()
			.stream()
			.filter(x -> x.getValue().attackOriginBlockHash() == attackOriginBlockHash)
			// Note that the x1 and x2 are switched in the comparison to achieve reversed sort order
			.sorted((x1, x2) -> Integer.compare(x2.getValue().attackOriginBlockOffset(), x1.getValue().attackOriginBlockOffset()))
			.map(x -> x.getKey())
			.findFirst()
			.orElse(null);
		
		if (hashOfLatestForkedBlock == null) {
			// No forked block found
			return attackOriginBlockHash;
		}
		
		return hashOfLatestForkedBlock;
	}
	
	public String getHashOfFirstMinedAttackOriginBlock() {
		Entry<String, BlockToOverrideStorageItem> entry =_blockToOverrideStorageItems
				.entrySet()
				.stream()
				.min((b1, b2) -> Long.compare(b1.getValue().getFirstMinedBlock().getBlockMinedTimestamp(), b2.getValue().getFirstMinedBlock().getBlockMinedTimestamp()))
				.get(); // Call get, because it is assumed that the hashmap is not empty (check in the only method that calls this method)
			
		return entry.getKey();
	}
	
	public void addBlock(Block block) {
		
		if (AttackerUtils.isBlockABlockToOverride(block)) {
			addToBlocksToOverrideItems(block);
		} else {
			addToForkedBlocksItems(block);
		}
	}
	
	private void addToBlocksToOverrideItems(Block block) {
		BlockToOverrideStorageItem blockStorageItem;
		
		if (!_blockToOverrideStorageItems.containsKey(block.getPreviousHash())) {
			blockStorageItem = new BlockToOverrideStorageItem(block.getPreviousHash());
			_blockToOverrideStorageItems.put(block.getPreviousHash(), blockStorageItem);
		}
		else {
			blockStorageItem = _blockToOverrideStorageItems.get(block.getPreviousHash());
		}
		
		blockStorageItem.addBlock(block);
	}
	
	private void addToForkedBlocksItems(Block block) {
		// Store the same attack origin block hash for the new added block
		ForkedBlockInfoItem forkedBlockInfo = _forkedBlockInfoByForkedBlockHashes.getOrDefault(block.getPreviousHash(), null);
		if (forkedBlockInfo != null) {
			ForkedBlockInfoItem newForkedBlockInfo = new ForkedBlockInfoItem(
					forkedBlockInfo.attackOriginBlockHash(),
					forkedBlockInfo.attackOriginBlockOffset() + 1);
			_forkedBlockInfoByForkedBlockHashes.put(block.getHash(), newForkedBlockInfo);
		} else {
			// First forked block after attack origin block
			
			if (_blockToOverrideStorageItems.containsKey(block.getPreviousHash())) {
				// Attack origin block does not exist and is the previous block -> store with minimal offset 1
				ForkedBlockInfoItem newForkedBlockInfo = new ForkedBlockInfoItem(
						block.getPreviousHash(),
						1);
				_forkedBlockInfoByForkedBlockHashes.put(block.getHash(), newForkedBlockInfo);
			} else {
				// Attack origin block is not present -> store as orphaned block
			}
		}
	}
}

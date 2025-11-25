package org.palladiosimulator.blockchainsystems.core.orphanblockpool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.OrphanBlockPool;

public class OrphanBlockPoolImpl extends BlockchainNodeObject implements OrphanBlockPool {

    private final HashMap<String, Set<Block>> _orphanBlocks;

    public OrphanBlockPoolImpl() {
        _orphanBlocks = new HashMap<>();
    }

    @Override
    public Set<Block> getBlocksByPreviousBlockHash(String previousBlockHash) {
        return _orphanBlocks.getOrDefault(previousBlockHash, Collections.emptySet());
    }

    @Override
    public void storeBlock(Block block) {
        Set<Block> blockSet = _orphanBlocks.getOrDefault(block.getPreviousHash(), null);
        if (blockSet == null) {
            blockSet = new HashSet<Block>();
            _orphanBlocks.put(block.getPreviousHash(), blockSet);
        }

        blockSet.add(block);

        logBlockStoredEvent(block);
    }

    private void logBlockStoredEvent(Block block) {
        if (!getTraceEventLogger().isEventTypeEnabled(BlockStoredInOrphanPoolTraceEvent.EVENT_TYPE)) {
            return;
        }

        BlockStoredInOrphanPoolTraceEvent event = new BlockStoredInOrphanPoolTraceEvent(
                getSimulationContext().getSystemClock().getCurrentTime(),
                block);

        getTraceEventLogger().logEvent(event);
    }

    @Override
    public void dispatchEvent(Event event) {
    }

}

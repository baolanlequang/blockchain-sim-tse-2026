package org.palladiosimulator.blockchainsystems.doublespending.behavior;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.blockchainsystems.core.block.abstractions.Block;
import org.palladiosimulator.blockchainsystems.core.common.BlockchainNodeObject;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationContext;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainMaliciousNodesIdProvider;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.BlockchainSystemNodeContext;
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkEndpoint;

/**
 * 
 * Contains utils used by multiple attack phases.
 * 
 * @author Yannik Sproll
 *
 */
public class AttackerUtils {
	
	private AttackerUtils() { } // No instance of this class should be created

	public static SimulationContext simulationContext;

	public static void distributeBlockToMaliciousNodes(
			Block block, 
			BlockchainSystemNodeContext context,
			BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
		Set<String> maliciousMinerIds = maliciousNodesIdProvider.getMaliciousNodeIds();
		
		Set<P2PNetworkEndpoint> maliciousNeighborEndpoints = context
				.getNetworkInterface()
				.getNeighbors()
				.stream()
				.filter(x -> maliciousMinerIds.contains(x.getEndpointId())) // Filter honest neighbors
				.collect(Collectors.toSet());


		MaliciousEventHandler handler = new MaliciousEventHandler(context.getResourcePower(), maliciousNeighborEndpoints);
		handler.initialize(simulationContext);
		handler.logEvent();


		// System.out.println("blockchain length: " + context.getBlockchain().getLength());

		context.getBlockPropagationStrategy().distribute(block, maliciousNeighborEndpoints);
	}
	
	public static void distributeBlockToHonestNodes(
			Block block,
			BlockchainSystemNodeContext context,
			BlockchainMaliciousNodesIdProvider maliciousNodesIdProvider) {
		Set<String> maliciousMinerIds = maliciousNodesIdProvider.getMaliciousNodeIds();
		
		Set<P2PNetworkEndpoint> maliciousNeighborEndpoints = context
				.getNetworkInterface()
				.getNeighbors()
				.stream()
				.filter(x -> !maliciousMinerIds.contains(x.getEndpointId())) // Filter malicious neighbors
				.collect(Collectors.toSet());

		context.getBlockPropagationStrategy().distribute(block, maliciousNeighborEndpoints);
	}
	
	public static boolean isMaliciousBlock(
			Block block) {
		return block.hasTag(MALICIOUS_BLOCK_ATTRIBUTE_NAME);
	}
	
	public static boolean isBlockABlockToOverride(
			Block block) {
		return block.hasTag(BLOCK_TO_OVERRIDE_NAME);
	}
	
	public static boolean isBlockABlockForkedBlock(
			Block block) {
		return block.hasTag(FORKED_BLOCK_NAME);
	}
	
	
	public final static String MALICIOUS_BLOCK_ATTRIBUTE_NAME = "MaliciousBlock";
	public final static String BLOCK_TO_OVERRIDE_NAME = "BlockToOverride";
	public final static String FORKED_BLOCK_NAME = "ForkedBlock";
	
	private static HashSet<String> _forkedBlockAttributes;
	
	public static HashSet<String> getForkedBlockAttributes() {
		if (_forkedBlockAttributes == null) {
			_forkedBlockAttributes = new HashSet<String>();
			_forkedBlockAttributes.add(MALICIOUS_BLOCK_ATTRIBUTE_NAME);
			_forkedBlockAttributes.add(FORKED_BLOCK_NAME);
		}
		
		return _forkedBlockAttributes;
	}
	
	private static HashSet<String> _blockToOverrideAttributes;
	
	public static HashSet<String> getBlocksToOverrideAttributes() {
		if(_blockToOverrideAttributes == null) {
			_blockToOverrideAttributes = new HashSet<String>();
			_blockToOverrideAttributes.add(MALICIOUS_BLOCK_ATTRIBUTE_NAME);
			_blockToOverrideAttributes.add(BLOCK_TO_OVERRIDE_NAME);
		}
		
		return _blockToOverrideAttributes;
	}
}

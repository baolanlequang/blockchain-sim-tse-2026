package org.palladiosimulator.blockchainsystems.doublespending.util;

import java.util.HashSet;

import org.palladiosimulator.blockchainsystems.core.system.BlockchainSystemNode;

public final class TagUtils {

	private TagUtils() {
	}
	
	public final static String MALICIOUS_NODE_TAG = "MaliciousNode";
	
	private static HashSet<String> _maliciousNodeTags;
	
	public static HashSet<String> getMaliciousNodeTags() {
		if (_maliciousNodeTags == null) {
			_maliciousNodeTags = new HashSet<String>();
			_maliciousNodeTags.add(MALICIOUS_NODE_TAG);
		}
		
		return _maliciousNodeTags;
	}
	
	public static boolean isMalicioiusNode(BlockchainSystemNode node) {
		return node.hasTag(MALICIOUS_NODE_TAG);
	}
}

package org.palladiosimulator.blockchainsystems.threesim.creation.network.explicit

import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult

/**
 * Result of creating a P2P network with explicit node definitions.
 *
 * @author Davis Riedel
 */
data class ExplicitP2PNetworkCreationResult(
  override val createdNetwork: P2PNetwork
) : P2PNetworkCreationResult
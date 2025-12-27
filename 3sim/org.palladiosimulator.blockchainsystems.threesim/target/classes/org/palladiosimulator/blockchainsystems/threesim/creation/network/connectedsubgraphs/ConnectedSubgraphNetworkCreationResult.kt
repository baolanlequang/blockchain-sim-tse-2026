package org.palladiosimulator.blockchainsystems.threesim.creation.network.connectedsubgraphs

import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetworkCreationResult

/**
 * Result of creating a P2P network with connected subgraph definition.
 *
 * @property createdNetwork The created P2P network.
 * @property nodeIdToNodeTemplateIdMapping A mapping from node IDs to their corresponding node template IDs.
 *
 * @author Davis Riedel
 */
data class ConnectedSubgraphNetworkCreationResult(
  override val createdNetwork: P2PNetwork,
  val nodeIdToNodeTemplateIdMapping: HashMap<String, String>
) : P2PNetworkCreationResult
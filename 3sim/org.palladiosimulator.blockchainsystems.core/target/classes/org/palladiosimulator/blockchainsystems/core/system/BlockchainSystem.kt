package org.palladiosimulator.blockchainsystems.core.system

import org.palladiosimulator.blockchainsystems.core.common.BlockchainSimulationObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegions
import org.palladiosimulator.blockchainsystems.core.system.abstractions.P2PNetwork
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.TransactionSubmissionProcess
import java.util.random.RandomGenerator

/**
 * The [BlockchainSystem] class represents a blockchain system,
 * consisting of a set of blockchain system nodes and a P2P network.
 *
 * @author Yannik Sproll, Davis Riedel
 */
class BlockchainSystem(
  id: String,
  name: String,
  val network: P2PNetwork,
  val geographicalRegions: GeographicalRegions,
  val nodes: HashSet<BlockchainSystemNode>,
  val transactionSubmissionProcess: TransactionSubmissionProcess,
  val blockReward: Double,
  private val recipientRandomGenerator: RandomGenerator = RandomGenerator.of("Random")
) : BlockchainSimulationObject(id, name) {
  private var orderedRecipientNodeIds: List<String> = emptyList()

  public override fun onInitialize() {
    if (nodes.isEmpty()) throw IllegalStateException("Blockchain system contains no validating nodes")
    orderedRecipientNodeIds = nodes.map { it.id }.sorted()

    network.initialize(simulationContext)

    nodes.forEach {
      it.initialize(simulationContext)
      transactionSubmissionProcess.addOnTransactionSubmittedCallbackSubscriber(it)
    }

    transactionSubmissionProcess.setOnSelectRecipientNodeIdCallback { selectRecipientNodeId() }
    transactionSubmissionProcess.initialize(simulationContext)
    transactionSubmissionProcess.startTransactionSubmissionProcess()
  }

  public override fun onCleanup() {
    network.cleanup()
    nodes.forEach { it.cleanup() }

    transactionSubmissionProcess.cleanup()
  }

  override fun dispatchEvent(event: Event) {
  }

  /**
   * Select a random node to submit the transaction to
   */
  private fun selectRecipientNodeId(): String {
    if (orderedRecipientNodeIds.isEmpty()) {
      throw IllegalStateException("Blockchain system contains no validating nodes")
    }
    return orderedRecipientNodeIds[
      recipientRandomGenerator.nextInt(orderedRecipientNodeIds.size)
    ]
  }
}
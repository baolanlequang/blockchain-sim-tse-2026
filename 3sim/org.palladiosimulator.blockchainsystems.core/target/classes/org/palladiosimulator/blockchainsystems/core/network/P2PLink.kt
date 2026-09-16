package org.palladiosimulator.blockchainsystems.core.network

import org.palladiosimulator.blockchainsystems.core.common.P2PNetworkObject
import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event
import org.palladiosimulator.blockchainsystems.core.common.abstractions.SimulationLifecycleAwareValueProvider
import org.palladiosimulator.blockchainsystems.core.system.abstractions.Message
import kotlin.math.roundToLong

/**
 * Unidirectional link between two [P2PNode]s in a P2P network.
 *
 * @author Davis Riedel, Yannik Sproll
 */
class P2PLink(
  private val latencyValueProvider: SimulationLifecycleAwareValueProvider<Long>,
  private val throughputValueProvider: SimulationLifecycleAwareValueProvider<Long>,
  var bandwidthValueProvider: SimulationLifecycleAwareValueProvider<Double>,
  val fromNode: P2PNode,
  val toNode: P2PNode
) : P2PNetworkObject() {
  override fun onInitialize() {
    super.onInitialize()
    latencyValueProvider.initialize(simulationContext)
    throughputValueProvider.initialize(simulationContext)
    bandwidthValueProvider.initialize(simulationContext)
  }

  override fun onCleanup() {
    super.onCleanup()
    latencyValueProvider.cleanup()
    throughputValueProvider.cleanup()
    bandwidthValueProvider.cleanup()
  }

  fun send(messageContent: Message) {
    // MessageSentEvent used to be raised at the current simulation timestamp,
    // which EventCoordinator dispatches synchronously before raiseEvent returns.
    // Execute that zero-delay transition directly and allocate only the future
    // delivery event that actually needs to live in the event queue.
    handleMessageSent(messageContent, toNode, fromNode)
  }

  override fun dispatchEvent(event: Event) {
    when (event.eventType) {
      MessageDroppedEvent.EVENT_TYPE -> handleMessageDroppedEvent(event as MessageDroppedEvent)
      MessageReceivedEvent.EVENT_TYPE -> handleMessageReceivedEvent(event as MessageReceivedEvent)
      MessageSentEvent.EVENT_TYPE -> handleMessageSentEvent(event as MessageSentEvent)
    }
  }

  private fun handleMessageReceivedEvent(event: MessageReceivedEvent) {
    event
      .recipientNode
      .onReceive(
        event.message,
        event.senderNode
      )
  }

  private fun handleMessageDroppedEvent(event: MessageDroppedEvent) {
    event
      .senderNode
      .onMessageDropped(
        event.message,
        event.recipientNode
      )
  }

  private fun handleMessageSentEvent(event: MessageSentEvent) {
    handleMessageSent(event.message, event.recipientNode, event.senderNode)
  }

  private fun handleMessageSent(
    message: Message,
    recipientNode: P2PNode,
    senderNode: P2PNode
  ) {
    val bps = throughputValueProvider.getValue() // in bits per second
    val firstBandwidthSample = bandwidthValueProvider.getValue()
    val bandwidth = if (firstBandwidthSample.isNaN()) {
      0.0
    } else {
      // Preserve the legacy provider-call count/order exactly. In refined runs
      // this provider is fixed, but other models may supply a stateful provider.
      bandwidthValueProvider.getValue()
    } // in Mbit/s

    if (bps <= 0 || bandwidth <= 0) {
      // A dropped message has zero modeled delay, so notify synchronously just as
      // the former current-time MessageDroppedEvent did.
      senderNode.onMessageDropped(message, recipientNode)
      return
    }

    val latency = latencyValueProvider.getValue() // in ms
    val messageSize = message.size.toLong() // in byte

    // Refined model: T_ij = S / B_ij^eff + L_ij. `bandwidth` is in
    // Mbit/s, message size is in bytes, and latency is in milliseconds.
    val serializationDelay = ((messageSize * 8.0 * 1000.0) / (bandwidth * 1_000_000.0)).roundToLong()
    val transmissionDuration = latency + serializationDelay

    simulationContext.eventCoordinator.raiseEvent(
      MessageReceivedEvent(
        message,
        simulationContext.systemClock.currentTime + transmissionDuration,
        this,
        recipientNode,
        senderNode
      )
    )
  }
}
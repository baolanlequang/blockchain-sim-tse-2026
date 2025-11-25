package org.palladiosimulator.blockchainsystems.core.transaction.abstractions

import org.palladiosimulator.blockchainsystems.core.propagation.Propagatable

interface Transaction : Propagatable {
  /**
   * Unique identifier of the transaction.
   */
  val txId: String

  /**
   * The size of the transaction in bytes.
   */
  val size: Int

  /**
   * The time when the transaction was created.
   */
  val creationTime: Long

  /**
   * The ID of the sender of the transaction.
   */
  val senderId: String

  /**
   * The ID of the recipient of the transaction.
   */
  val recipientId: String

  /**
   * The amount of the transaction.
   */
  val amount: Double

  /**
   * The fee associated with the transaction.
   */
  val fee: Double
}
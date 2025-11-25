package org.palladiosimulator.blockchainsystems.core.transaction

import kotlinx.serialization.Serializable
import org.palladiosimulator.blockchainsystems.core.transaction.abstractions.Transaction

/**
 * Implementation of the [Transaction] interface.
 *
 * @property txId The unique identifier of the transaction.
 * @property size The size of the transaction in bytes.
 * @property creationTime The time when the transaction was created.
 * @property senderId The ID of the sender of the transaction.
 * @property recipientId The ID of the recipient of the transaction.
 * @property amount The amount of the transaction.
 * @property fee The fee associated with the transaction.
 *
 * @author Davis Riedel
 */
@Serializable
class TransactionImpl(
  override val txId: String,
  override val size: Int,
  override val creationTime: Long,
  override val senderId: String,
  override val recipientId: String,
  override val amount: Double,
  override val fee: Double
) : Transaction
package org.palladiosimulator.blockchainsystems.threesim.creation

import org.palladiosimulator.blockchainsystems.core.common.abstractions.ValueProvider
import org.palladiosimulator.blockchainsystems.core.utils.RandomValueProvider
import java.util.random.RandomGenerator
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionPropertiesSpecification
import org.palladiosimulator.blockchainsystems.core.transaction.TransactionProperties

/**
 * Adapter for a [ValueProvider] that provides [TransactionProperties]
 *
 * @param randomValueProvider the underlying random value provider that provides the transaction properties.
 *
 * @author Davis Riedel
 */
class TransactionPropertiesValueProviderAdapter(
  private val randomValueProvider: RandomValueProvider<TransactionProperties>,
) : ValueProvider<TransactionProperties> {
  override fun getValue(): TransactionProperties {
    return randomValueProvider.getValue()
  }

  companion object {
    fun create(
      transactionPropertiesSpecification: TransactionPropertiesSpecification,
      randomGenerator: RandomGenerator
    ): TransactionPropertiesValueProviderAdapter {
      val valuesToProbabilitiesMapping = transactionPropertiesSpecification.values.associate {
        TransactionProperties(it.size, it.fee, it.amount) to it.probability
      }

      val valueProvider = RandomValueProvider.create(
        valuesToProbabilitiesMapping,
        randomGenerator
      )

      return TransactionPropertiesValueProviderAdapter(valueProvider)
    }
  }
}
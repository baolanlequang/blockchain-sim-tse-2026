package org.palladiosimulator.blockchainsystems.core.propagation

/**
 * Interface for a factory that produces instances of [PropagationStrategy]
 *
 * @author Yannik Sproll, Davis Riedel
 */
interface PropagationStrategyFactory<E : Propagatable> {
  /**
   * Creates an instance of @code{PropagationStrategy}
   *
   * @return a @code{PropagationStrategy} instance
   */
  fun createPropagationStrategy(): PropagationStrategy<E>
}
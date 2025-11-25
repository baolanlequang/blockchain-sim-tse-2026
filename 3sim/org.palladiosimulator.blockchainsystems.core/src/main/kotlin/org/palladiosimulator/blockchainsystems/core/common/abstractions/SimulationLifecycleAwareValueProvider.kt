package org.palladiosimulator.blockchainsystems.core.common.abstractions

/**
 * The @code{ValueProvider} interface is an abstraction used for
 * parametrization of simulation components.
 *
 * @param <TValue> the type of the values that are supplied
 * @author Davis Riedel
 */
interface SimulationLifecycleAwareValueProvider<TValue> : ValueProvider<TValue>, SimulationLifecycleAware
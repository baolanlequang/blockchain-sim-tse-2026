package org.palladiosimulator.blockchainsystems.core.common.abstractions;

/**
 * The @code{ValueProvider} interface is an abstraction used for
 * parametrization of simulation components.
 *
 * @param <TValue> the type of the values that are supplied
 * @author Yannik Sproll
 */
public interface ValueProvider<TValue> {

    /**
     * Returns a value of the specified generic type.
     *
     * @return a value of the specified generic type
     */
    TValue getValue();
}

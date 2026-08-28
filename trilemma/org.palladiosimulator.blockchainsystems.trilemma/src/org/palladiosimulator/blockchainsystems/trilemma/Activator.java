package org.palladiosimulator.blockchainsystems.trilemma;

/**
 * Classpath anchor for standalone resource initialization.
 *
 * <p>This bundle does not declare a Bundle-Activator, so extending
 * org.eclipse.core.runtime.Plugin is unnecessary and makes the standalone
 * runner depend on a PDE-only type in its public constructor path.</p>
 */
public final class Activator {
    private Activator() {
        // No instances.
    }
}

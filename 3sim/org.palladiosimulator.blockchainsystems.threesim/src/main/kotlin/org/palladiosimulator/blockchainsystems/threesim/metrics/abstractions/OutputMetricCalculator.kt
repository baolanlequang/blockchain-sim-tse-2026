package org.palladiosimulator.blockchainsystems.threesim.metrics.abstractions

/**
 * Interface for a class that computes an output metric
 *
 * @author Davis Riedel
 */
interface OutputMetricCalculator<M : OutputMetric<*>> {
  fun calculate(): M;
}
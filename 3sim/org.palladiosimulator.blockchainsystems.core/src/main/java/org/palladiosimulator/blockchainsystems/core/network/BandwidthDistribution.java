package org.palladiosimulator.blockchainsystems.core.network;

import org.apache.commons.math3.distribution.GammaDistribution;

public class BandwidthDistribution {
	/**
     * Calculate alpha (a) based on CV (H) and number of connection m.
     */
    public static double calibrateAlpha(double targetH, int m) {
        if (m <= 1) return 1.0;
        // Formular: a = (m - 1 - H^2) / (m * H^2)
        double hSquared = targetH * targetH;
        if (targetH == 0.0) {
        	hSquared = 0.0001 * 0.0001; // hSquared a > 0
        }
        double a = (m - 1 - hSquared) / (m * hSquared);
        return Math.max(a, 0.001); // Guarantee a > 0
    }

    /**
     * Simulate Dirichlet distribution by using Gamma Distribution.
     */
    public static double[] generateDirichlet(double alpha, int m) {
        GammaDistribution gamma = new GammaDistribution(alpha, 1.0);
        double[] samples = new double[m];
        double sum = 0;

        for (int i = 0; i < m; i++) {
            samples[i] = gamma.sample();
            sum += samples[i];
        }

        for (int i = 0; i < m; i++) {
            samples[i] /= sum;
        }
        return samples;
    }
}

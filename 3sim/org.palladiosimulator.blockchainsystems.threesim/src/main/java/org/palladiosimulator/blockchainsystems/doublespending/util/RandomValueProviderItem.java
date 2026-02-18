package org.palladiosimulator.blockchainsystems.doublespending.util;

public final record RandomValueProviderItem<T>(
		double selectionValueBorder,
		T value) {
}

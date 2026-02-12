package org.palladiosimulator.blockchainsystems.doublespending.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.random.RandomGenerator;

public class RandomValueProvider<T> {

	private final RandomGenerator _randomGenerator;
	private final TreeMap<Integer, RandomValueProviderItem<T>> _items;
	
	public RandomValueProvider(TreeMap<Integer, RandomValueProviderItem<T>> items, RandomGenerator generator) {
		_items = items;
		_randomGenerator = generator;
	}
	
	public T getValue() {
		double selectionValue = _randomGenerator.nextDouble();
		
		int index  = 0;
		
		double currentLowerBorder = 0;
		double currentUpperBorder = _items.get(0).selectionValueBorder();
		
		while (!isInRange(selectionValue, currentLowerBorder, currentUpperBorder) && indexIsInValidRange(index)) {
			currentLowerBorder = currentUpperBorder;
			currentUpperBorder = _items.get(index + 1).selectionValueBorder();
			index++;
		}
		
		return _items.get(index).value();
	}
	
	private boolean indexIsInValidRange(int index) {
		return index < _items.size() - 1;
	}
	
	private static boolean isInRange(double value, double lowerBorder, double upperBorder) {
		return lowerBorder <= value && value < upperBorder;
	}
	
	private final static double RANDOM_VALUES_SUM_MAX_DEVIATION = 0.005;
	
	public static <T> RandomValueProvider<T> create(HashMap<T, Double> randomValues, RandomGenerator generator) {
		double randomValuesSum = randomValues
				.values()
				.stream()
				.mapToDouble(x -> x.doubleValue())
				.sum();
		
		// May not be greater than 1, but if it is not exactly one it can be tolerated
		// -> Last value gets the gap as an additional probability
		if (randomValuesSum > 1.0) {
			throw new IllegalArgumentException("The sum of the provided probabilities is greater than one.");
		}
		
		double deviation = 1.0  - randomValuesSum;
		if (deviation > RANDOM_VALUES_SUM_MAX_DEVIATION) {
			throw new IllegalArgumentException("The sum of the provided probabilities deviates more than allowed deviation maximum (0.005).");
		}
		
		TreeMap<Integer, RandomValueProviderItem<T>> items = new TreeMap<Integer, RandomValueProviderItem<T>>();
		
		
		int index = 0;
		double currentSelectionValueSum = 0;
		
		for (Entry<T, Double> entry : randomValues.entrySet()) {
			
			currentSelectionValueSum += entry.getValue();
			items.put(index, new RandomValueProviderItem<T>(currentSelectionValueSum, entry.getKey()));
			
			index++;
		}
		
		return new RandomValueProvider<T>(items, generator);
	}
}

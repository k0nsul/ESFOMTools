package com.esfom.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MathUtils {
	private MathUtils() {}

	private static Double key1;
	private static Double key2;
	private static Double val1;
	private static Double val2;

	/**
	 * Return rounded number
	 * @param value
	 * @param decimalPlaces - numbers after "."
	 * @return rounded value
	 */
	public static double round(double value, int decimalPlaces) {
		return Math.round(value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
	}

	/**
	 * Interpolate y of x from TreeMap
	 * 
	 * @param x
	 * @param map
	 *            - only TreeMap
	 * @return y of x
	 */
	public static double interpolate(double x, TreeMap<Double, Double> map) {

		List<Double> keyList = new ArrayList<Double>(map.keySet());

		if (map.containsKey(x)) {
			return map.get(x);
		} else if (x < keyList.get(0)) {
			return isLessThanMinKey(x, keyList, map);
		} else if (x > keyList.get(keyList.size() - 1)) {
			return isMoreThanMaxKey(x, keyList, map);
		} else {
			return isKeyInRange(x, keyList, map);
		}
	}
	
	public static double interpolate(BigDecimal x, TreeMap<Double, Double> map) {
		return interpolate(x.doubleValue(), map);
		
	}

	private static double isKeyInRange(double x, List<Double> keyList, TreeMap<Double, Double> map) {
		int first = 0;
		int last = keyList.size() - 1;
		while (true) {
			int middle = (last + first) / 2;
			if (first == last - 1) {
				key1 = keyList.get(first);
				key2 = keyList.get(last);
				val1 = map.get(key1);
				val2 = map.get(key2);
				double K = K(key1, key2, val1, val2);
				double dK = x - key1;
				return val1 + dK * K;
			} else if (x < keyList.get(middle)) {
				last = middle;
			} else if (x > keyList.get(middle)) {
				first = middle;
			}
		}
	}

	private static double isMoreThanMaxKey(double x, List<Double> keyList, TreeMap<Double, Double> map) {
		key1 = keyList.get(keyList.size() - 2);
		key2 = keyList.get(keyList.size() - 1);
		val1 = map.get(key1);
		val2 = map.get(key2);
		double K = K(key1, key2, val1, val2);
		double dK = x - key2;
		return val2 + dK * K;
	}

	private static double isLessThanMinKey(double x, List<Double> keyList, TreeMap<Double, Double> map) {
		key1 = keyList.get(0);
		key2 = keyList.get(1);
		val1 = map.get(key1);
		val2 = map.get(key2);
		double K = K(key1, key2, val1, val2);
		double dK = key1 - x;
		return val1 - dK * K;
	}

	private static double K(double key1, double key2, double val1, double val2) {
		return (val2 - val1) / (key2 - key1);
	}

}

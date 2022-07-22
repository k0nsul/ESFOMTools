package com.esfom.math;

import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Combinatorics Utils for calculation:<br>
 * Permutations - P,<br>
 * Combinations - C<br>
 * and Accomodations - A<br>
 * WITH and WITHOUT repeats
 * 
 * @author firsov.k.n@gmail.com
 * @since 2022
 *
 */
public class Combinatorics {
	
	private static Logger log = LoggerFactory.getLogger(Combinatorics.class);

	/**
	 * P(n) = n!
	 * 
	 * @param n - set
	 * @return possible permutations in n set
	 */
	public static BigInteger permutationsNoRepeats(int n) throws IllegalArgumentException {
		return factorial(n);
	}

	/**
	 * P_(n1,n2...nk) = n!/(n1!*n2!*...*nk!)
	 * 
	 * @param n  - set
	 * @param ns - list of repeats {n1,n2...nk}
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static BigInteger permutationsWithRepeats(int n, List<Integer> ns) throws IllegalArgumentException {
		BigInteger nom = factorial(n);
		BigInteger denom = ns.stream().map(e -> factorial(e)).reduce(BigInteger.ONE, BigInteger::multiply);
		return nom.divide(denom);
	}

	/**
	 * Simpliest version of P_ P_(n1,n2...nk) = n!/(n1!*n2!*...*nk!)
	 * 
	 * parameter <b>n</b> is sum of list's ni
	 * 
	 * @param ns - list of repeats {n1,n2...nk}
	 * @return
	 */
	public static BigInteger permutationsWithRepeats(List<Integer> ns) throws IllegalArgumentException {
		int n = ns.stream().reduce(0, Integer::sum);
		return permutationsWithRepeats(n, ns);
	}

	/**
	 * A_(n,k) = n^k
	 * 
	 * @param n - set
	 * @param k - subset
	 * @return possible placements WITH repeats [n^k]
	 */
	public static BigInteger accomodationsWithRepeats(int n, int k) throws IllegalArgumentException {
		return BigInteger.valueOf(n).pow(k);
	}

	/**
	 * A(n,k) = P(n)/(n - k)! = n!/(n-k)!
	 * 
	 * @param n - set
	 * @param k - subset
	 * @return possible placements WITHOUT repeats [n!/(n-k)!]
	 */
	public static BigInteger accomodationsNoRepeats(int n, int k) throws IllegalArgumentException {
		argumentsCheck(n, k);
		int from = n - k + 1;
		return rangeMultiply(from, n);
	}

	/**
	 * C(n,k) = A(n,k) / k! = n!/(k!*(n-k)!)
	 * 
	 * @param n -set
	 * @param k - subset
	 * @return possible combinations WITHOUT repeats
	 */
	public static BigInteger combinationsNoRepeats(int n, int k) throws IllegalArgumentException {
		argumentsCheck(n, k);
		if (n == k)
			return BigInteger.ONE;
		int delta = n - k;
		int from;
		int denominator;
		if (k > delta) {
			from = k + 1;
			denominator = delta;
		} else {
			from = n - k + 1;
			denominator = k;

		}
		return rangeMultiply(from, n).divide(factorial(denominator));
	}

	/**
	 * C_(n,k) = (n+k-1)!/(k!*(n-1)!)
	 * 
	 * @param n -set
	 * @param k - subset
	 * @return possible combinations WITH repeats
	 */
	public static BigInteger combinationsWithRepeats(int n, int k) throws IllegalArgumentException {
		argumentsCheck(n, k);
		int numerator = n + k - 1;
		int delta = n - 1;
		int from;
		int denominator;
		if (k > delta) {
			from = k + 1;
			denominator = delta;
		} else {
			from = n - k + 1;
			denominator = k;

		}
		return rangeMultiply(from, numerator).divide(factorial(denominator));
	}

	/**
	 * Math factorial
	 * 
	 * @param n - factorial of number n
	 * @return n!
	 */
	public static BigInteger factorial(int n) throws IllegalArgumentException {
		BigInteger result = BigInteger.ONE;
		if (n < 0)
			throw new IllegalArgumentException("Number must be positive");
		if (n == 0)
			return result;
		for (int i = 1; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return from * (from+1) * (from + 2)...*to
	 */
	private static BigInteger rangeMultiply(int from, int to) {
		if (from > to)
			throw new IllegalArgumentException("[from > to] : from must be greater or equal to");
		if (from == 0 || to == 0)
			return BigInteger.ZERO;
		if (from == to)
			return BigInteger.valueOf(from);
		BigInteger result = BigInteger.valueOf(from);
		for (int i = from + 1; i <= to; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}

	private static void argumentsCheck(int n, int k) throws IllegalArgumentException {
		if (k > n)
			throw new IllegalArgumentException("[k > n] : n must be greater or equal k");
	}

	/**
	 * <table border=1>
	 * <tr>
	 * <td><b>FORMULA</b></td>
	 * <td><b>FUNCTION NAME</b></td>
	 * <td><b>ORDER</b></td>
	 * <td><b>REPEATS</b></td>
	 * <td><b>SUBSET</b></td>
	 * </tr>
	 * <tr>
	 * <td><i>A_(n,k) = n^k</i></td>
	 * <td>{@link Combinatorics#accomodationsWithRepeats(int, int)
	 * accomodationsWithRepeats}</td>
	 * <td><b>true</b></td>
	 * <td><b>true</b></td>
	 * <td><b>true</b></td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td><i>A(n,k) = P(n)/(n - k)! = n!/(n-k)!</i></td>
	 * <td>{@link Combinatorics#accomodationsNoRepeats(int, int)
	 * accomodationsNoRepeats}</td>
	 * <td><b>true</b></td>
	 * <td>false</td>
	 * <td><b>true</b></td>
	 * </tr>
	 * <tr>
	 * <td><i>P(n) = n!</i></td>
	 * <td>{@link Combinatorics#permutationsNoRepeats(int)
	 * permutationsNoRepeats}</td>
	 * <td><b>true</b></td>
	 * <td>false</td>
	 * <td>false</td>
	 * </tr>
	 * <tr>
	 * <td><i>C(n,k) = A(n,k) / k! = n!/(k!*(n-k)!)</i></td>
	 * <td>{@link Combinatorics#combinationsNoRepeats(int, int)
	 * combinationsNoRepeats}</td>
	 * <td>false</td>
	 * <td>false</td>
	 * <td><b>true</b></td>
	 * </tr>
	 * <td><i>C_(n,k) = (n+k-1)!/(k!*(n-1)!)</i></td>
	 * <td>{@link Combinatorics#combinationsWithRepeats(int, int)
	 * combinationsWithRepeats}</td>
	 * <td>false</td>
	 * <td><b>true</b></td>
	 * <td><b>true</b></td>
	 * </tr>
	 * </table>
	 * For Permutations WITH repeats <i>P_(n1,n2...nk) = n!/(n1!*n2!*...*nk!)</i>
	 * use overloaded method {@link Combinatorics#permutationsWithRepeats(int, List)
	 * permutationsWithRepeats} directly
	 * 
	 * @param n       - set
	 * @param k       - subset. NULL if absent
	 * @param order   - true if order is important
	 * @param repeats - true if repeats available (WITH repeats)
	 * @return result based on the most appropriate formula
	 * @throws IllegalArgumentException
	 */
	public static BigInteger smartCalc(int n, Integer k, boolean order, boolean repeats)
			throws IllegalArgumentException {
		if (order && repeats && k != null) {
			log.trace("Calc with accomodationsWithRepeats");
			return accomodationsWithRepeats(n, k);
		} else if (order && !repeats && k != null) {
			log.trace("Calc with accomodationsNoRepeats");
			return accomodationsNoRepeats(n, k);
		} else if (order && (k == null || n == k)) {
			log.trace("Calc with permutationsNoRepeats");
			return permutationsNoRepeats(n);
		} else if (!order && !repeats && k != null) {
			log.trace("Calc with combinationsNoRepeats");
			return combinationsNoRepeats(n, k);
		} else if (!order && repeats && k != null) {
			log.trace("Calc with combinationsWithRepeats");
			return combinationsWithRepeats(n, k);
		}
		throw new IllegalArgumentException("Have NO formula for this arguments set.");
	}

}

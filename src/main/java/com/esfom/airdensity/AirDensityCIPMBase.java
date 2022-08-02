package com.esfom.airdensity;

import com.esfom.munit.Converter;

/**
 * The abstract class {@code AirDensityCIPMBase} contains constants and methods
 * for calculating the moist air density in accordance with the "Equations for
 * the Determination of the Density of Moist Air".
 *
 * @author Firsov Konstantin <firsov.k.n@gmail.com>
 * @since JDK8
 */

abstract class AirDensityCIPMBase implements AirDensity {

	protected String EQUATION_NAME;

	/**
	 * The {@code double} value <i>R/(J mol<sup>-1</sup> K<sup>-1</sup>)</i>, the
	 * molar gas constant.
	 */
	protected double MOLAR_GAS_CONSTANT;

	public double getMOLAR_GAS_CONSTANT() {
		return MOLAR_GAS_CONSTANT;
	}

	/**
	 * The {@code double} value <i>Ma/(kg mol<sup>-1</sup>)</i>, the molar mass of
	 * dry air.
	 */
	protected double MOLAR_MASS_OF_DRY_AIR;

	public double getMOLAR_MASS_OF_DRY_AIR() {
		return MOLAR_MASS_OF_DRY_AIR;
	}

	/**
	 * The {@code double} value <i>Mv/(g mol<sup>-1</sup>)</i>, the molar mass of
	 * water.
	 */
	protected double MOLAR_MASS_OF_WATER;

	/**
	 * The {@code double} value <i>Xco<sub>2</sub>/(mol mol<sup>-1</sup>)</i>, the
	 * average mole fraction of carbon dioxide in laboratory air.
	 */
	protected double CARBON_DIOXIDE_FRACTION;

	// Constants for saturationVapourPressure();
	protected double A;
	protected double B;
	protected double C;
	protected double D;

	/**
	 * Returns the vapour pressure at saturation value <i>Pv/(Pa)</i>.
	 * 
	 * @param airTemperature
	 *            current air temperature in laboratory in <sup>O</sup>C.
	 * @return vapour pressure at saturation value of the argument.
	 */
	private double saturationVapourPressure(double airTemperature) {
		double T = Converter.CelsiusToKelvin(airTemperature);
		return 1 * Math.exp(A * Math.pow(T, 2) + B * T + C + D / T);
	}

	// Constants for compressibilityFactor();
	protected double a0;
	protected double a1;
	protected double a2;
	protected double b0;
	protected double b1;
	protected double c0;
	protected double c1;
	protected double d;
	protected double e;

	/**
	 * Returns the compressibility factor value <i>Z</i>.
	 * 
	 * @param airTemperature
	 *            current air temperature in laboratory in <sup>O</sup>C.
	 * @param airHumidity
	 *            current relative humidity in laboratory (relative humidity reading
	 *            of "53%" is expressed as 0.53).
	 * @param pressure
	 *            atmospheric pressure in pascals.
	 * @return compressibility factor value of the arguments.
	 */
	private double compressibilityFactor(double airTemperature, double airHumidity, double pressure) {
		double T = Converter.CelsiusToKelvin(airTemperature);
		double Xv = waterVapourMoleFraction(airTemperature, airHumidity, pressure);
		double Q1 = pressure / T;
		double Q2 = a0 + a1 * airTemperature + a2 * Math.pow(airTemperature, 2) + (b0 + b1 * airTemperature) * Xv
				+ (c0 + c1 * airTemperature) * Math.pow(Xv, 2);
		double Q3 = Math.pow(pressure, 2) / Math.pow(T, 2);
		double Q4 = d + e * Math.pow(Xv, 2);
		return 1 - Q1 * Q2 + Q3 * Q4;
	}

	/**
	 * Arguments checker for allowable range
	 * 
	 * @param airTemperature
	 *            current air temperature in laboratory in <sup>O</sup>C.
	 * @param airHumidity
	 *            current relative humidity in laboratory (relative humidity reading
	 *            of "53%" is expressed as 0.53).
	 * @param pressure
	 *            atmospheric pressure in pascals.
	 * @return throw Exception.
	 */
	private double waterVapourMoleFraction(double airTemperature, double airHumidity, double pressure) {
		return airHumidity
				* (enhancementFactor(airTemperature, pressure) * saturationVapourPressure(airTemperature) / pressure);
	}

	/**
	 * Returns the molar mass of dry air value <i>Ma/(kg mol<sup>-1</sup>)</i>, this
	 * function should be used to improve the estimate of the molar mass of dry air
	 * if a measurement of Xco<sub>2</sub> is avaliable.
	 * 
	 * @param carbonDioxideFraction
	 *            mole fraction of carbon dioxide in laboratory air.
	 * @return estimated molar mass of dry air.
	 */
	public double dryAirMolarMass(double carbonDioxideFraction) {
		return MOLAR_MASS_OF_DRY_AIR + (12.011 * (carbonDioxideFraction - CARBON_DIOXIDE_FRACTION)) * 10E-3;
	}

	/**
	 * Returns the enhancement factor value<i>f</i>.
	 * 
	 * @param airTemperature
	 *            current air temperature in laboratory in <sup>O</sup>C.
	 * @param pressure
	 *            atmospheric pressure in pascals.
	 * @return enhancement factor of the arguments.
	 */
	private  double enhancementFactor(double airTemperature, double pressure) {
		final double alpha = 1.00062;
		final double beta = 3.14E-8;
		final double gamma = 5.6E-7;
		return alpha + beta * pressure + gamma * Math.pow(airTemperature, 2);
	}

	@Override
	public double getAirDensity(double airTemperature, double airHumidity, double pressure,
			double carbonDioxideFraction) {
		
		double Ma = dryAirMolarMass(carbonDioxideFraction);
		double Q1 = (pressure * Ma) / (compressibilityFactor(airTemperature, airHumidity, pressure) * MOLAR_GAS_CONSTANT
				* Converter.CelsiusToKelvin(airTemperature));
		double Q2 = waterVapourMoleFraction(airTemperature, airHumidity, pressure) * (1 - MOLAR_MASS_OF_WATER / Ma);
		return Q1 * (1 - Q2);
	}

	@Override
	public double getAirDensity(double airTemperature, double airHumidity, double pressure) {
		return getAirDensity(airTemperature, airHumidity, pressure, CARBON_DIOXIDE_FRACTION);
	}

	@Override
	public String getEquationName() {
		return this.EQUATION_NAME;
	}

}

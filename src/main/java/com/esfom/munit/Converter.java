package com.esfom.munit;

import java.text.MessageFormat;

public class Converter {
	private Converter() {
	}

	/**
	 * Converter temperature from degrees <i>Celcius</i> to degrees <i>Kelvin</i>.
	 * 
	 * @param temperature current air temperature in laboratory in <sup>O</sup>C.
	 * @return recalculated temperature in <i>Kelvin</i>.
	 */
	public static double CelsiusToKelvin(double temperature) {
		return Constants.KELVIN_ZERO + temperature;
	}

	/**
	 * Converter temperature from degrees <i>Celcius</i> to degrees
	 * <i>Fahrenheit</i>.
	 * 
	 * @param temperature current air temperature in laboratory in <i>Celcius</i>.
	 * @return recalculated temperature in <i>Fahrenheit</i>.
	 */
	public static double CelsiusToFahrenheit(double temperature) {
		return (9 / 5) * temperature + 32;
	}

	/**
	 * Converter temperature from degrees <i>Fahrenheit</i> to degrees
	 * <i>Celcius</i>.
	 * 
	 * @param temperature current air temperature in laboratory in
	 *                    <i>Fahrenheit</i>.
	 * @return recalculated temperature in <i>Celcius</i>.
	 */
	public static double FahrenheitToCelsius(double temperature) {
		return (5 / 9) * (temperature - 32);
	}

	/**
	 * Equation relating ITS-90 temperature, to IPTS-68 temperature.
	 * 
	 * 
	 * @param temperature IPTS-68 temperature <i>Celcius</i>.
	 * @return ITS-90 temperature in <i>Celcius</i>.
	 */
	public static double IPTS68ToIPTS90(double temperature) {
		if (temperature <= 40) {
			// The equation for the temperature range 0 to 40 �C
			return 0.0002 + 0.99975 * temperature;
		}
		if (temperature <= 100) {
			// In the temperature range 0 to 100 �C the equation is
			return 0.0005 + 0.9997333 * temperature;
		} else {
			throw new IllegalArgumentException("Water temperature is out of range 0 - 100 C");

		}
	}

	/**
	 * Converter angle from <i>degrees</i> to <i>radians</i>.
	 * 
	 * @param angleDegree current air temperature in laboratory in <sup>O</sup>C.
	 * @return recalculated angle in <i>radians</i>.
	 */
	public static double DegreesToRadians(double angleDegree) {
		return angleDegree * (Math.PI / 180);
	}

	/**
	 * Converter pressure from <i>Pascal</i> to <i>mm Hg</i>.
	 * 
	 * @param pressure in laboratory in <i>Pascal</i>.
	 * @return recalculated pressure in <i>mm Hg</i>.
	 */
	public static double PascalToMMHg(double pressure) {
		return pressure / Constants.MMHG_IN_PASCAL;
	}

	/**
	 * Converter pressure from <i>mm Hg</i> to <i>Pascal</i>.
	 * 
	 * @param pressure in laboratory in <i>mm Hg</i>.
	 * @return recalculated pressure in <i>Pascal</i>.
	 */
	public static double MMHgToPascal(double pressure) {
		return pressure * Constants.MMHG_IN_PASCAL;
	}

	/**
	 * Converter <i>Specific gravity</i> to <i>API gravity</i>.
	 * 
	 * @param sg <i>Specific gravity (oilDensity / baseDensity)</i>.
	 * @return <i>API gravity</i>.
	 */
	public static double SGToAPI(double sg) {
		return 141.5 / sg - 131.5;
	}

	/**
	 * Converter <i>Density</i> to <i>API gravity</i>.
	 * 
	 * @param density in kg/m3
	 * @return <i>API gravity</i>.
	 */
	public static double DensityToAPI(double density) {
		return SGToAPI(DensityToSG(density));
	}

	/**
	 * Converter <i>API gravity</i> to <i>Specific gravity</i>.
	 * 
	 * @param api <i>API gravity</i>.
	 * @return <i>Specific gravity</i>.
	 */
	public static double APIToSg(double api) {
		return 141.5 / (api + 131.5);
	}

	/**
	 * Converter <i>API gravity</i> to <i>density</i>.
	 * 
	 * @param api
	 * @return density in kg/m3
	 */
	public static double APIToDensity(double api) {
		return SGToDensity(APIToSg(api));
	}

	/**
	 * Converter <i>Density</i> to <i>Specific gravity</i>. Specific gravity is the
	 * ratio of the density of a substance to the density of a reference substance.
	 * 
	 * @param density     <i>kg/m<sup>3</sup></i>.
	 * @param baseDensity - density of a reference substance
	 *                    <i>kg/m<sup>3</sup></i>.
	 * @return <i>Specific gravity</i>.
	 */
	public static double DensityToSG(double density, double baseDensity) {
		return density / baseDensity;
	}

	/**
	 * Converter <i>Density</i> to <i>Specific gravity</i>. Specific gravity is the
	 * ratio of the density of a substance to the density of a reference substance.
	 * There the reference substance for liquids is nearly always water at its
	 * densest (at 4 �C / 39.2 �F) baseDensity - 1000 <i>kg/m<sup>3</sup></i>
	 * 
	 * @param density <i>kg/m<sup>3</sup></i>.
	 * @return <i>Specific gravity</i>.
	 */
	public static double DensityToSG(double density) {
		return DensityToSG(density, Constants.DENSITY_OF_WATER_4C);
	}

	/**
	 * Converter <i>Specific gravity</i> to <i>Density</i>. Specific gravity is the
	 * ratio of the density of a substance to the density of a reference substance.
	 * 
	 * @param sg          <i>Specific gravity</i>.
	 * @param baseDensity - density of a reference substance
	 *                    <i>kg/m<sup>3</sup></i>.
	 * @return density <i>kg/m<sup>3</sup></i>.
	 */
	public static double SGToDensity(double sg, double baseDensity) {
		return sg * baseDensity;
	}

	/**
	 * Converter <i>Specific gravity</i> to <i>Density</i>. Specific gravity is the
	 * ratio of the density of a substance to the density of a reference substance.
	 * There the reference substance for liquids is nearly always water at its
	 * densest (at 4 �C / 39.2 �F) baseDensity - 1000 <i>kg/m<sup>3</sup></i>
	 * 
	 * @param sg          <i>Specific gravity</i>.
	 * @param baseDensity - density of a reference substance
	 *                    <i>kg/m<sup>3</sup></i>.
	 * @return density <i>kg/m<sup>3</sup></i>.
	 */
	public static double SGToDensity(double sg) {
		return sg * Constants.DENSITY_OF_WATER_4C;
	}

	/**
	 * 
	 * @param substanceIndex index of component for calculate fraction
	 * @param substancesAmount array of components amounts
	 * @return mole fraction of index component
	 */
	public static double moleFraction(int substanceIndex, double[] substancesAmount){
		return calcFraction(substanceIndex, substancesAmount);
	}
	
	/**
	 * 
	 * @param substanceIndex index of component for calculate fraction
	 * @param substancesMass array of components masses
	 * @param substancesMolarMass array of components molar masses
	 * @return mole fraction of index component
	 */
	public static double moleFraction(int substanceIndex, double[] substancesMass, double[] substancesMolarMass){
		if (substancesMass.length != substancesMolarMass.length) {
			throw new IllegalArgumentException(MessageFormat.format("substancesMass length {0} != substancesMolarMass length {1}", substancesMass.length, substancesMolarMass.length));
		}else if(substanceIndex > substancesMass.length || substanceIndex < 0) {
			throw new IllegalArgumentException(MessageFormat.format("Substance index {0} is out of substances array", substanceIndex));
		}
		double[] substancesAmount = new double[substancesMass.length];
		for(int i = 0; i < substancesMass.length; i++) {
			substancesAmount[i] = substanceAmount(substancesMass[i], substancesMolarMass[i]);
		}
		
		return moleFraction(substanceIndex, substancesAmount);
	}
	
	/**
	 * 
	 * @param substanceIndex index of component for calculate fraction
	 * @param substancesMass array of components masses
	 * @return mass fraction of index component
	 */
	public static double massFraction(int substanceIndex, double[] substancesMass){
		return calcFraction(substanceIndex, substancesMass);
	}
	
	/**
	 * 
	 * @param substanceIndex index of component for calculate fraction
	 * @param substancesAmount array of components amounts
	 * @param substancesMolarMass array of components molar masses
	 * @return mass fraction of index component
	 */
	public static double massFraction(int substanceIndex, double[] substancesAmount, double[] substancesMolarMass){
		if (substancesAmount.length != substancesMolarMass.length) {
			throw new IllegalArgumentException(MessageFormat.format("substancesAmount length {0} != substancesMolarMass length {1}", substancesAmount.length, substancesMolarMass.length));
		}else if(substanceIndex > substancesAmount.length || substanceIndex < 0) {
			throw new IllegalArgumentException(MessageFormat.format("Substance index {0} is out of substances array", substanceIndex));
		}
		double[] substancesMass = new double[substancesAmount.length];
		for(int i = 0; i < substancesAmount.length; i++) {
			substancesMass[i] = substanceMass(substancesAmount[i], substancesMolarMass[i]);
		}
		
		return massFraction(substanceIndex, substancesMass);
	}
	
	/**
	 * 
	 * @param substanceIndex index of component for calculate fraction
	 * @param substancesVolume array of components volumes
	 * @return volume fraction of index component
	 */
	public static double volumeFraction(int substanceIndex, double[] substancesVolume){
		return calcFraction(substanceIndex, substancesVolume);
	}
	
	private static double calcFraction(int index, double[] amount) {
		if (index > amount.length || index < 0) {
			throw new IllegalArgumentException(MessageFormat.format("Substance index {0} is out of substances array", index));
		}
		
		double sum = 0;
		for (int i = 0; i < amount.length; i++) {
			sum += amount[i];
		}

		return amount[index] / sum;
	}
	
	/**
	 * 
	 * @param mass component mass [gramm]
	 * @param molarMass component molar mass [gramm/mol]
	 * @return amount of substance [mol]
	 */
	public static double substanceAmount(double mass, double molarMass) {
		return mass / molarMass;
	}
	
	/**
	 * 
	 * @param amount component amount [mol]
	 * @param molarMass component molar mass [gramm/mol]
	 * @return mass of substance [gramm]
	 */
	public static double substanceMass(double amount, double molarMass) {
		return amount * molarMass;
	}

}

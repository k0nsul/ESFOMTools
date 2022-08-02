package com.esfom.airdensity;
import static com.esfom.math.MathUtils.round;
import static com.esfom.munit.Converter.CelsiusToKelvin;

public interface AirDensity {
	
	/**
     * Returns standart air density <i>(kg/m<sup>3</sup>)</i>.
     * 
     * @param   airTemperature current air temperature in laboratory in <sup>O</sup>C.
     * @return  standart air density 1.205 <i>(kg/m<sup>3</sup>)</i>.
     */
	default double getStandartAirDensity() {
		return 1.205;
	}
	
	/**
     * Returns the current approximate air density.
     * 
     * @param   airTemperature current air temperature in laboratory in <sup>O</sup>C.
     * @param   airHumidity current relative humidity in laboratory (relative humidity reading of "53%" is expressed as 0.53).
     * @param   pressure atmospheric pressure in pascals.
     * @return  current air density of the arguments (kg/m<sup>3</sup>).
     */
	default double getApproximateAirDensity(double airTemperature, double airHumidity, double pressure) {
		double Q1 = 0.34848 * pressure / 100;
		double Q2 = 0.009024 * (airHumidity * 100) * Math.pow(Math.E, 0.0612 * airTemperature);
		double Q3 = CelsiusToKelvin(airTemperature);
		return (Q1 - Q2) / Q3;
	}
	
	/**
     * Returns the simple calculated air density.
     * <p>There:<br>
     * PRESSURE = 101325 Pa<br>
     * NORMAL_AIR_DENSITY = 1.2 kg/m<sup>3</sup><br>
     * GRAVITY_ACCELERATION = 9.81 m/s<sup>2</sup><br>
     * 
     * @param   h height of laboratory above sea level in meters.
     * @return  current air density of the arguments (kg/m<sup>3</sup>).
     */
	default double getSimpleAirDensity(double h) {
		double Q1 = (-1.2 / 101325) * 9.81 * h ;
		double res = 1.2 * Math.exp(Q1);
		return round(res, 3);
	}
	
	/**
     * Returns the current air density.
     * 
     * @param   airTemperature current air temperature in laboratory in (<sup>O</sup>C).
     * @param   airHumidity current relative humidity in laboratory (relative humidity reading of "53%" is expressed as 0.53).
     * @param   pressure atmospheric pressure in pascals (Pa).
     * @param   carbonDioxideFraction   mole fraction of carbon dioxide in laboratory air.
     * @return  current air density of the arguments (kg/m<sup>3</sup>).
     */
	public double getAirDensity(double airTemperature, double airHumidity, double pressure, double carbonDioxideFraction);
	
	/**
     * Returns the current air density without mole fraction of carbon dioxide in laboratory air measurements.
     * 
     * @param   airTemperature current air temperature in laboratory in <sup>O</sup>C.
     * @param   airHumidity current relative humidity in laboratory (relative humidity reading of "53%" is expressed as 0.53).
     * @param   pressure atmospheric pressure in pascals.
     * @return  current air density of the arguments (kg/m<sup>3</sup>).
     */
	public double getAirDensity(double airTemperature, double airHumidity, double pressure);
	
	/**
     * Returns the current equation name.
     * 
     * @return  current equation name.
     */
	public String getEquationName();
	
	/**
	 * Return 1 - e/8000;
	 * Where e is StandartAirDensity
	 */
	public default double getK() {
		return 1 - getStandartAirDensity()/8000;
	}
	
	/**
	 * Return 1 - e/8000;
	 * Where e is current air density
	 */
	public default double getK(double airTemperature, double airHumidity, double pressure) {
		return 1 - getAirDensity(airTemperature, airHumidity, pressure)/8000;
	}
		
}

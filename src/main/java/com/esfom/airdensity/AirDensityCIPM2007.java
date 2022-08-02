package com.esfom.airdensity;

/**
 * The class {@code AirDensityCIPM2007} contains constants and methods for
 * calculating the moist air density in accordance with the "Equation for the
 * Determination of the Density of Moist Air (CIPM-2007)".
 *
 * @author Firsov Konstantin <firsov.k.n@gmail.com>
 * @since JDK8
 */

public class AirDensityCIPM2007 extends AirDensityCIPMBase {

	public AirDensityCIPM2007() {
		
		this.EQUATION_NAME = "CIPM-2007";
		this.MOLAR_GAS_CONSTANT = 8.3144721515151515;
		this.MOLAR_MASS_OF_DRY_AIR = 28.96546E-3;
		this.MOLAR_MASS_OF_WATER = 18.0152817E-3;
		this.CARBON_DIOXIDE_FRACTION = 0.0004;

		this.A = 1.2378847E-5;
		this.B = -1.9121316E-2;
		this.C = 33.93711047;
		this.D = -6.3431645E3;

		this.a0 = 1.58123E-6;
		this.a1 = -2.9331E-8;
		this.a2 = 1.1043E-10;
		this.b0 = 5.707E-6;
		this.b1 = -2.051E-8;
		this.c0 = 1.9898E-4;
		this.c1 = -2.376E-6;
		this.d = 1.83E-11;
		this.e = -0.765E-8;
	}

}

package com.esfom.airdensity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirFactory {

	private static final Logger log = LoggerFactory.getLogger(AirFactory.class);

	public Air getAir(Class<? extends AirDensity> clazz) {
		Air air = new Air();
		try {
			air.setAirDensity(clazz.newInstance());
			// Create a standart Air (T=20.0, H=0.50 [50%] , P=101325 Pa [760 mmHg])
			air.set(20.00, 0.50, 101325);
		} catch (InstantiationException e) {
			log.error(e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getLocalizedMessage());
		}
		return air;
	}

}

package com.esfom.airdensity;

public class Air {
	
	private double temperature;
	private double humidity;
	private double pressure;
	private AirDensity airDensity;
	
	public Air() {
		super();
		this.airDensity = new AirDensityCIPM2007();
	}
	
	public void set(double temperature, double humidity, double pressure) {
		this.setTemperature(temperature);
		this.setHumidity(humidity);
		this.setPressure(pressure);
	}
	
	public void setAirDensity(AirDensity airDensity) {
		this.airDensity = airDensity;
	}

	public double getAirDensity() {
		return this.airDensity.getAirDensity(temperature, humidity, pressure);
	}

	public double getK() {
		return this.airDensity.getK(temperature, humidity, pressure);

	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	@Override
	public String toString() {
		return "Air [temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + "]";
	}

	public Double getApproximateAirDensity() {
		return this.airDensity.getApproximateAirDensity(temperature, humidity, pressure);
	}

}

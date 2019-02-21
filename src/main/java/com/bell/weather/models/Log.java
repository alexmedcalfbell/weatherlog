package com.bell.weather.models;

/**
 * Models a log file.
 *
 * @author Alexander Medcalf-Bell
 */
public class Log {

	private String light;

	private String rgb;

	private String motion;

	private String heading;

	private String temperature;

	private String pressure;

	public String getLight() {
		return light;
	}

	public Log setLight(String light) {
		this.light = light;
		return this;
	}

	public String getRgb() {
		return rgb;
	}

	public Log setRgb(String rgb) {
		this.rgb = rgb;
		return this;
	}

	public String getMotion() {
		return motion;
	}

	public Log setMotion(String motion) {
		this.motion = motion;
		return this;
	}

	public String getHeading() {
		return heading;
	}

	public Log setHeading(String heading) {
		this.heading = heading;
		return this;
	}

	public String getTemperature() {
		return temperature;
	}

	public Log setTemperature(String temperature) {
		this.temperature = temperature;
		return this;
	}

	public String getPressure() {
		return pressure;
	}

	public Log setPressure(String pressure) {
		this.pressure = pressure;
		return this;
	}
}

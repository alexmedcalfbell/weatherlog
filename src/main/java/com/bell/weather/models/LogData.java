package com.bell.weather.models;

/**
 * Models a log file.
 *
 * @author Alexander Medcalf-Bell
 */
public class LogData {

    private int id;
    private String light;
    private String rgb;
    private String motion;
    private String heading;
    private String temperature;
    private String pressure;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLight() {
        return light;
    }

    public LogData setLight(final String light) {
        this.light = light;
        return this;
    }

    public String getRgb() {
        return rgb;
    }

    public LogData setRgb(final String rgb) {
        this.rgb = rgb;
        return this;
    }

    public String getMotion() {
        return motion;
    }

    public LogData setMotion(final String motion) {
        this.motion = motion;
        return this;
    }

    public String getHeading() {
        return heading;
    }

    public LogData setHeading(final String heading) {
        this.heading = heading;
        return this;
    }

    public String getTemperature() {
        return temperature;
    }

    public LogData setTemperature(final String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getPressure() {
        return pressure;
    }

    public LogData setPressure(final String pressure) {
        this.pressure = pressure;
        return this;
    }
}

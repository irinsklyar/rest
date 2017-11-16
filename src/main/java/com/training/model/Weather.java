package com.training.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "weather")
public class Weather {

    private String city;
    private String temperature;
    private String wind;
    private String error;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        if (city != null ? !city.equals(weather.city) : weather.city != null) return false;
        if (temperature != null ? !temperature.equals(weather.temperature) : weather.temperature != null) return false;
        if (wind != null ? !wind.equals(weather.wind) : weather.wind != null) return false;
        return error != null ? error.equals(weather.error) : weather.error == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        result = 31 * result + (wind != null ? wind.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }
}

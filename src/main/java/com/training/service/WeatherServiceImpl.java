package com.training.service;

import com.training.model.Weather;
import com.training.process.WeatherProcess;

public class WeatherServiceImpl implements WeatherService {
    private WeatherProcess weatherProcess;

    public WeatherServiceImpl(WeatherProcess weatherProcess) {
        this.weatherProcess = weatherProcess;
    }

    public Weather parseWeather(String city) {
        return weatherProcess.getWeather(city);
    }

}

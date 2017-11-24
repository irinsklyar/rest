package com.training.service;

import com.training.model.Weather;
import com.training.process.FetchWeatherProcess;
import com.training.repo.WeatherDaoImpl;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherServiceImpl implements WeatherService {
    private static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());
    private FetchWeatherProcess connToApi;
    private WeatherDaoImpl weatherDao;

    public WeatherServiceImpl(FetchWeatherProcess connToApi, WeatherDaoImpl weatherDao) {
        this.connToApi = connToApi;
        this.weatherDao = weatherDao;
    }

    public Weather getWeatherbyCityName(String city) {
        Weather weather = new Weather();
        try {
            JSONObject rawData = new JSONObject(connToApi.getWeatherInfo(city));

            if (rawData.get("cod").toString().equals("404")) {
                weather.setError(rawData.get("cod").toString());
                weather.setCity(city + " city name was not found");
            } else {
                weather.setTemperature(Double.toString(rawData.getJSONObject("main").getDouble("temp")));
                weather.setWind(Integer.toString(rawData.getJSONObject("wind").getInt("speed")));
                weather.setCity(city);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        weatherDao.saveWeather(weather);

        return weather;
    }
}

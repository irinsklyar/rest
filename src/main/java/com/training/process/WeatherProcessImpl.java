package com.training.process;

import com.training.model.Weather;
import com.training.repo.WeatherDaoImpl;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherProcessImpl implements WeatherProcess {
    private static final Logger LOGGER = Logger.getLogger(WeatherProcessImpl.class.getName());
    private FetchWeatherProcess connToApi;
    private WeatherDaoImpl weatherDao;

    public Weather getWeather(String city) {
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
        weatherDao.insertRecord(weather, "records");

        return weather;
    }

    public WeatherDaoImpl getWeatherDao() {
        return weatherDao;
    }

    public void setWeatherDao(WeatherDaoImpl weatherDao) {
        this.weatherDao = weatherDao;
    }

    public FetchWeatherProcess getConnToApi() {
        return connToApi;
    }

    public void setConnToApi(FetchWeatherProcess connToApi) {
        this.connToApi = connToApi;
    }
}

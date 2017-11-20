package com.training.process;

import com.training.model.Weather;
import com.training.repo.CassandraConnector;
import com.training.repo.WeatherDaoImpl;

public class Test {

    public static void main(String[] args) {

        WeatherProcessImpl weatherProcess = new WeatherProcessImpl();

        FetchWeatherProcess connToApi = new FetchWeatherProcess();
        weatherProcess.setConnToApi(connToApi);

        WeatherDaoImpl weatherDao = new WeatherDaoImpl();
        weatherDao.setCassandraConnector(new CassandraConnector());
        weatherProcess.setWeatherDao(weatherDao);

        Weather weather = weatherProcess.getWeather("Kharkiv");

        System.out.println(weather.getTemperature());
    }
}

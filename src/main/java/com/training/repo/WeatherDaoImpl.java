package com.training.repo;

import com.training.model.Weather;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class WeatherDaoImpl {
    private CassandraConnector cassandraConnector;

    public WeatherDaoImpl(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    public Properties getConfig() {
        Properties prop = null;
        try {
            prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        } catch (IOException io) {
            io.printStackTrace();
        }
        return prop;
    }

    public void saveWeather(Weather weather) {
        Properties prop = getConfig();
        cassandraConnector.connect(prop.getProperty("node"), Integer.parseInt(prop.getProperty("port")));
        cassandraConnector.createKeyspace("weather_records", "SimpleStrategy", 1);
        cassandraConnector.createTable("weather_records.records");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("weather_records.records").append(" (id,city,temperature,time,wind) ")
                .append("VALUES (").append("uuid(),'").append(weather.getCity())
                .append("', '").append(weather.getTemperature())
                .append("', '").append(new Date().getTime())
                .append("', '").append(weather.getWind()).append("');");

        String query = sb.toString();
        cassandraConnector.getSession().execute(query);
        cassandraConnector.close();
    }
}



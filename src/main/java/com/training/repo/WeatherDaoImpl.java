package com.training.repo;

import com.training.model.Weather;

import java.util.Date;

public class WeatherDaoImpl implements WeatherDao {
    private CassandraConnector cassandraConnector;

    public void insertRecord(Weather weather, String table) {
        cassandraConnector.connect("127.0.0.1", 9042);
        cassandraConnector.createKeyspace("weather_records", "SimpleStrategy", 1);
        cassandraConnector.createTable("weather_records." + table);
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("weather_records." + table).append(" (id,city,temperature,time,wind) ")
                .append("VALUES (").append("uuid(),'").append(weather.getCity())
                .append("', '").append(weather.getTemperature())
                .append("', '").append(new Date().getTime())
                .append("', '").append(weather.getWind()).append("');");

        String query = sb.toString();
        cassandraConnector.getSession().execute(query);
        cassandraConnector.close();
    }

    public CassandraConnector getCassandraConnector() {
        return cassandraConnector;
    }

    public void setCassandraConnector(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }
}



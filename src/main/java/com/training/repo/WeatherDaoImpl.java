package com.training.repo;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.training.model.Weather;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.datastax.driver.core.querybuilder.QueryBuilder.uuid;

public class WeatherDaoImpl {
    private static final Logger LOGGER = Logger.getLogger(WeatherDaoImpl.class.getName());
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
            LOGGER.log(Level.SEVERE, io.toString(), io);
        }
        return prop;
    }

    public void saveWeather(Weather weather) {
        Properties prop = getConfig();
        cassandraConnector.connect(prop.getProperty("node"), Integer.parseInt(prop.getProperty("port")));
        cassandraConnector.createKeyspace("weather_records", "SimpleStrategy", 1);
        cassandraConnector.createTable("weather_records.records");

        List<String> names = Arrays.asList("id,city,temperature,time,wind".split(","));
        List<Object> values = new ArrayList<>();
        values.add(uuid());
        values.add(weather.getCity());
        values.add(weather.getTemperature());
        values.add(new Date().getTime());
        values.add(weather.getWind());

        Insert preparedStatement = QueryBuilder.insertInto("weather_records","records")
                .values(names, values);
        cassandraConnector.getSession().execute(preparedStatement);
        cassandraConnector.close();
    }

    public String findWeather() {
        Properties prop = getConfig();
        cassandraConnector.connect(prop.getProperty("node"), Integer.parseInt(prop.getProperty("port")));
        Session session = cassandraConnector.getSession();
        RegularStatement city = QueryBuilder.select("time", "city", "temperature", "wind")
                .from("records")
                .allowFiltering()
                .where()
                .and(QueryBuilder.gt("time", Instant.now().minus(60, ChronoUnit.MINUTES).toEpochMilli()));
        PreparedStatement getDetailsStmt = session.prepare(city);

        BoundStatement boundStatement = getDetailsStmt.bind();
        ResultSet execute = session.executeAsync(boundStatement).getUninterruptibly();
        List<Weather> results = execute.all().stream()
                .map(this::constructWeather)
                .collect(Collectors.toList());
        cassandraConnector.close();
        String collect = results.stream()
                .map(a -> String.join(", ", a.getTime(), a.getCity(), a.getTemperature(), a.getWind()))
                .collect(Collectors.joining("\n"));

        return collect;
    }

    private Weather constructWeather(Row row) {
        Weather weather = new Weather();
        weather.setTime(String.valueOf(row.getTimestamp("time")));
        weather.setCity(row.getString("city"));
        weather.setTemperature(row.getString("temperature"));
        weather.setWind(row.getString("wind"));
        return weather;
    }
}



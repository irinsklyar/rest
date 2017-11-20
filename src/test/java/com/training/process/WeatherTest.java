package com.training.process;

import com.training.model.Weather;
import com.training.repo.WeatherDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WeatherTest {
    @Mock
    private FetchWeatherProcess connToApi;
    @Mock
    private WeatherDaoImpl weatherDao;
    @InjectMocks
    private WeatherProcessImpl weatherProcess;

    @Test
    public void shouldReturnExpectedWeatherObject() throws Exception {
        //given
        Weather weatherExpected = new Weather();
        weatherExpected.setCity("Lviv");
        weatherExpected.setTemperature("277.15");
        weatherExpected.setWind("4");
        weatherExpected.setError(null);

        String jsonString = "{\"coord\":{\"lon\":24.02,\"lat\":49.84}," +
                "\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}]," +
                "\"base\":\"stations\"," +
                "\"main\":{\"temp\":277.15,\"pressure\":1021,\"humidity\":100,\"temp_min\":277.15,\"temp_max\":277.15}," +
                "\"visibility\":10000,\"wind\":{\"speed\":4,\"deg\":320}," +
                "\"clouds\":{\"all\":90},\"dt\":1510657200," +
                "\"sys\":{\"type\":1,\"id\":7361,\"message\":0.0042,\"country\":\"UA\",\"sunrise\":1510637720,\"sunset\":1510670456}," +
                "\"id\":702550,\"name\":\"Lviv\",\"cod\":200}";
        when(connToApi.getWeatherInfo("Lviv")).thenReturn(jsonString);
        //when
        Weather weatherActual = weatherProcess.getWeather("Lviv");
        //then
        assertEquals(weatherExpected, weatherActual);
    }

    @Test
    public void shouldReturnWeatherObjectWithErrorCode() throws Exception {
        //given
        Weather weatherExpected = new Weather();
        weatherExpected.setCity("Lvi city name wasn't found");
        weatherExpected.setTemperature(null);
        weatherExpected.setWind(null);
        weatherExpected.setError("404");
        String jsonString = "{\"cod\":\"404\",\"message\":\"city not found\"}";
        when(connToApi.getWeatherInfo("Lvi")).thenReturn(jsonString);
        //when
        Weather weatherActual = weatherProcess.getWeather("Lvi");
        //then
        assertEquals(weatherExpected, weatherActual);
    }

}

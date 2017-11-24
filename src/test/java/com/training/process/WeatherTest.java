package com.training.process;

import com.training.model.Weather;
import com.training.repo.WeatherDaoImpl;
import com.training.service.WeatherServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherTest {
    @Mock
    private FetchWeatherProcess connToApi;
    @Mock
    private WeatherDaoImpl weatherDao;
    @InjectMocks
    private WeatherServiceImpl weatherProcess;

    @Test
    public void shouldReturnExpectedWeatherObject() throws Exception {
        //given
        Weather weatherExpected = new Weather();
        weatherExpected.setCity("Lviv");
        weatherExpected.setTemperature("277.15");
        weatherExpected.setWind("4");
        weatherExpected.setError(null);
        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource("jsonstring1.json").toURI())));

        when(connToApi.getWeatherInfo("Lviv")).thenReturn(jsonString);
        //when
        Weather weatherActual = weatherProcess.getWeatherbyCityName("Lviv");
        //then
        assertEquals(weatherExpected, weatherActual);
    }

    @Test
    public void shouldReturnWeatherObjectWithErrorCode() throws Exception {
        //given
        Weather weatherExpected = new Weather();
        weatherExpected.setCity("Lvi city name was not found");
        weatherExpected.setTemperature(null);
        weatherExpected.setWind(null);
        weatherExpected.setError("404");
        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource("jsonstring2.json").toURI())));
        when(connToApi.getWeatherInfo("Lvi")).thenReturn(jsonString);
        //when
        Weather weatherActual = weatherProcess.getWeatherbyCityName("Lvi");
        //then
        assertEquals(weatherExpected, weatherActual);
    }
}

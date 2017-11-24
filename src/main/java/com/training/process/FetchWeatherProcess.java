package com.training.process;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

public class FetchWeatherProcess {
    private static final String URL_API = "http://api.openweathermap.org/data/2.5/weather";
    private static final String COUNTRY = "ua";
    private static final String APP_ID = "d8f9d06a92d4555ddf801b77091b8dc8";
    private static final String UNITS = "metric";

    public String getWeatherInfo(String city) throws IOException {
        UriBuilder uriBuilder = UriBuilder
                .fromPath(URL_API)
                .queryParam("q", "{city}", COUNTRY)
                .queryParam("appid", APP_ID)
                .queryParam("units", UNITS);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(new HttpGet(uriBuilder.build(city)));

        return EntityUtils.toString(response.getEntity());
    }
}

package com.training.process;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class FetchWeatherProcess {
    private static final String URL_PART1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String URL_PART2 = ",ua&appid=d8f9d06a92d4555ddf801b77091b8dc8&units=metric";

    public String getWeatherInfo(String city) throws IOException {
        String url = URL_PART1 + city + URL_PART2;
        HttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(new HttpGet(url));

        return EntityUtils.toString(response.getEntity());
    }
}

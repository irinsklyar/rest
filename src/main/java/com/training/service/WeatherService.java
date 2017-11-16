package com.training.service;

import com.training.model.Weather;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("weatherservice")
public interface WeatherService {
    @GET
    @Path("/{city}")
    @Produces("application/json")
    public Weather parseWeather(@PathParam("city") String city);
}

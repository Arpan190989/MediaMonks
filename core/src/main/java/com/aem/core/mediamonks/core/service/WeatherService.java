package com.aem.core.mediamonks.core.service;

import org.json.simple.JSONObject;


public interface WeatherService {
    
    public JSONObject getWeatherDetails(String city);

}

package com.aem.core.service;

import org.json.simple.JSONObject;


public interface WeatherService {
    
    public JSONObject getWeatherDetails(String city);

}

package com.example.weatherapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final String apiKey;
    private final RestTemplate restTemplate;

    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/forecast?";
    private static final String GEO_API_URL = "https://api.openweathermap.org/geo/1.0/direct?";

    public WeatherService(@Value("${openweathermap.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getGeoCoordinates(String city) {
        String url = GEO_API_URL + "q=" + city + "&limit=1&appid=" + apiKey;
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response != null && !response.isEmpty() ? response.get(0) : null;
    }

    public Map<String, Object> getWeatherForecast(double lat, double lon) {
        String url = WEATHER_API_URL + "lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + apiKey;
        return restTemplate.getForObject(url, Map.class);
    }
}
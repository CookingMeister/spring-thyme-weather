package com.example.weatherapp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        List<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
        }
        ).getBody();
        return response != null && !response.isEmpty() ? response.get(0) : null;
    }

    public Map<String, Object> getWeatherForecast(double lat, double lon) {
        String url = WEATHER_API_URL + "lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + apiKey;
        Map<String, Object> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
        }
        ).getBody();

        if (response != null) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM. d");
            for (Map<String, Object> forecast : forecastList) {
                long timestamp = ((Number) forecast.get("dt")).longValue();
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
                forecast.put("formattedDate", dateTime.format(formatter));
            }
        }
        return response;

    }
}

package com.example.weatherapp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
public class WeatherService {

     private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${api.key}")
    private String apiKey;

    // Use the default url string value if the property is not set
    @Value("${api.weather.url:https://api.openweathermap.org/data/2.5/forecast?}")
    private String apiWeatherUrl;
    // Use the default url string value if the property is not set
    @Value("${api.geo.url:https://api.openweathermap.org/geo/1.0/direct?}")
    private String apiGeoUrl;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
        public void init() {
            logger.info("WeatherService initialized with apiWeatherUrl: {} and apiGeoUrl: {}", apiWeatherUrl, apiGeoUrl);
        }

    public Map<String, Object> getGeoCoordinates(String city) {
        String url = apiGeoUrl + "q=" + city + "&limit=1&appid=" + apiKey;
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
        String url = apiWeatherUrl + "lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + apiKey;
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
                String formattedDate = dateTime.format(formatter);
                forecast.put("formattedDate", formattedDate);
            }
        }
        return response;
    }
}
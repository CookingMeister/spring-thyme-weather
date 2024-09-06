package com.example.weatherapp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {

        try {
            logger.info("Entering getWeather method");
            Map<String, Object> geoData = weatherService.getGeoCoordinates(city);
            if (geoData == null) {
                model.addAttribute("error", "City not found");
                return "index";
            }

            double lat = ((Number) geoData.get("lat")).doubleValue();
            double lon = ((Number) geoData.get("lon")).doubleValue();

            Map<String, Object> weatherData = weatherService.getWeatherForecast(lat, lon);
            model.addAttribute("weatherData", weatherData);

        } catch (Exception e) {
            logger.error("Error occurred while fetching weather data", e);
        }

        logger.info("Exiting getWeather method");
        return "weather";
    }
}

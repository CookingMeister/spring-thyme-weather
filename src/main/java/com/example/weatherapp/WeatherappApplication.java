package com.example.weatherapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherappApplication {

    private static final Logger logger = LoggerFactory.getLogger(WeatherappApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Weather App...");

        try {
            SpringApplication.run(WeatherappApplication.class, args);
            logger.info("Weather App started successfully.");
        } catch (Exception e) {
            logger.error("Error starting Weather App", e);
        }

        logger.info("Weather App is still running...");
    }
}

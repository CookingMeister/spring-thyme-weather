# spring-thyme-weather

This is a Spring Boot application that provides weather forecasts for cities using the OpenWeatherMap API.

## Features

- Search for weather forecasts by city name
- Display current weather conditions
- Show a 5-day weather forecast
- Responsive design using Bootstrap

## Prerequisites

- Java 21 or higher
- Maven
- OpenWeatherMap API key
- Docker (optional)

## Setup

1. Clone the repository:

   `git clone https://github.com/CookingMeister/spring-thyme-weather`

2. Navigate to the project directory:

   `cd weather-forecast-app`

3. Edit the `application.properties` file in the `src/main/resources` directory and add your OpenWeatherMap API key:

   `api.key=your_api_key_here`

   You can also specify the URLs for the OpenWeatherMap API endpoints in the `application.properties` file:

   `api.geo.url=https://api.openweathermap.org/geo/1.0/direct?`

   `api.weather.url=https://api.openweathermap.org/data/2.5/forecast?`

   Note: If you don't specify the `api.geo.url` and `api.weather.url`, the application will use the default URLs provided in the WeatherService.java file.

4. Build and run the application:

   Option 1 - Using JAR:

   `mvn clean install`

   `java -jar target/weatherapp-0.0.1-SNAPSHOT.jar`

   Option 2 - Using Docker:

   `docker build -t spring-thyme-weather .`

   `docker run -p 8080:8080 spring-thyme-weather`

5. Open a web browser and go to `http://localhost:8080`

## Usage

1. Enter a city name in the search box on the home page.
2. Click the "Get Weather" button to view the weather forecast.
3. The application will display the current weather and a 5-day forecast for the specified city.

## Technologies Used

- Spring Boot
- Thymeleaf
- Bootstrap
- OpenWeatherMap API
- Docker

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the [MIT License](LICENSE).

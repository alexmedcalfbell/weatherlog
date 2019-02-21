package com.bell.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application for presenting weather logs.
 * @author Alexander Medcalf-Bell
 */
@SpringBootApplication
public class WeatherApplication {

	public static void main(final String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}

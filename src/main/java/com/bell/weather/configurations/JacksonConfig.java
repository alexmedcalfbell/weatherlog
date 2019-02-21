package com.bell.weather.configurations;

import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson object mapper configuration.
 * @author Alexander Medcalf-Bell
 */
@Configuration
public class JacksonConfig {

	@Bean
	ObjectMapper objectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new JavaTimeModule());

		final StdDateFormat stdDateFormat = new StdDateFormat();
		stdDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		objectMapper.setDateFormat(stdDateFormat);

		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		return objectMapper;
	}
}

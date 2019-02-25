package com.bell.weather.controllers.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bell.weather.exceptions.PathNotConfiguredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WeatherLogAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherLogAdvice.class);

	private HandlerExceptionResolver handlerExceptionResolver;

	@Autowired
	public WeatherLogAdvice(HandlerExceptionResolver handlerExceptionResolver) {
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@ExceptionHandler(PathNotConfiguredException.class)
	public ModelAndView handleNestedException(final HttpServletRequest request, final HttpServletResponse response, final Exception exception) {

		LOGGER.error("PathNotConfiguredException", exception);
		return handlerExceptionResolver.resolveException(request, response, null, (Exception) exception.getCause());
	}
}

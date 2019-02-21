package com.bell.weather.controllers;

import java.io.IOException;

import com.bell.weather.services.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Main view Controller.
 * @author Alexander Medcalf-Bell
 */
@Controller
@RequestMapping({ "/", "logs" })
public class LogsController {

	private final LogService logService;

	@Autowired
	public LogsController(final LogService logService) {
		this.logService = logService;
	}

	// https://www.baeldung.com/thymeleaf-in-spring-mvc
	// https://www.baeldung.com/spring-thymeleaf-3

	@GetMapping//("logs")
	public String getLogs(final Model model) throws IOException {

		model.addAttribute("logs", logService.getLogs());

		return "log-list";
	}

	//TODO: Rework all this horrible nav stuff, do a single page app
	@PostMapping("/logold")
	public String getStats(@RequestParam("path") final String logPath, final Model model) throws IOException {

		model.addAttribute("log", logService.readLog(logPath));

		return "log-view";
	}
	//could just have a settimeout in js to call a responsebody method in a restcontroller?
}

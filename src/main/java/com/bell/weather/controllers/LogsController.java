package com.bell.weather.controllers;

import com.bell.weather.exceptions.PathNotConfiguredException;
import com.bell.weather.services.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Log View Controller.
 *
 * @author Alexander Medcalf-Bell
 */
@Controller
@RequestMapping({"/", "logs"})
public class LogsController {

	private final LogService logService;

	@Autowired
	public LogsController(final LogService logService) {
		this.logService = logService;
	}

	@GetMapping
	public String getLogs(final Model model) {
		try {
			model.addAttribute("logs", logService.getLogs());
		} catch (final PathNotConfiguredException e) {
			model.addAttribute("error", e.getMessage());
			return "error";
		}
		return "log-list";
	}

	//TODO: Could just have a settimeout in js to call a the datatables event?
	//TODO: Try graphing library e.g. https://www.chartjs.org/
}

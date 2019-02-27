package com.bell.weather.controllers;

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
@RequestMapping({"/stats"})
public class StatsController {

    private final LogService logService;

    @Autowired
    public StatsController(final LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public String stats(final Model model) {
        model.addAttribute("logs", logService.getLogs());
        return "stats";
    }

}

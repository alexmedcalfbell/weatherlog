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
    public String config(final Model model) {

        model.addAttribute("log", logService.readLog("/Users/abell/Documents/weather/enviroLONDONtoPETERBOROUGH.log"));

        return "stats";
    }

    //TODO: config page
    //TODO: Could just have a settimeout in js to call a the datatables event?

    //TODO: Try graphing library e.g. https://www.chartjs.org/
}

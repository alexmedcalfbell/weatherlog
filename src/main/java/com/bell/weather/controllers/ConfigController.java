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
@RequestMapping({"/config"})
public class ConfigController {

    private final LogService logService;

    @Autowired
    public ConfigController(final LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public String config(final Model model) {

        return "config";

    }

    //TODO: config page
    //TODO: Could just have a settimeout in js to call a the datatables event?

    //TODO: Try graphing library e.g. https://www.chartjs.org/
}

package com.bell.weather.controllers;

import com.bell.weather.models.LogData;
import com.bell.weather.services.LogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController for returning data via AJAX.
 *
 * @author Alexander Medcalf-Bell
 */
@RestController
public class DataController {

    private final LogService logService;

    @Autowired
    public DataController(final LogService logService) {
        this.logService = logService;
    }

    @PostMapping(
            value = "/log",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<LogData> readLog(@RequestParam("path") final String logPath) {
        return logService.readLog(logPath);
    }
}

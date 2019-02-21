package com.bell.weather.controllers;

import com.bell.weather.models.Log;
import com.bell.weather.services.LogService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final LogService logService;

    @Autowired
    public DataController(final LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/log")
    public Log readLog(@RequestParam("path") final String logPath) throws IOException {
        return logService.readLog(logPath);
    }
    //    public List<String> getReadLogPaths() throws IOException {
    //        return this.logService.getLogs();
    //    }

    //    // take param for path
    //    private Log fetchLogData() throws IOException {
    //        return this.logService.readLog();
    //    }

}

package com.bell.weather.controllers;

import com.bell.weather.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final LogService logService;

    @Autowired
    public DataController(final LogService logService) {
        this.logService = logService;
    }


    //    public List<String> getReadLogPaths() throws IOException {
    //        return this.logService.getLogs();
    //    }

    //    // take param for path
    //    private Log fetchLogData() throws IOException {
    //        return this.logService.readLog();
    //    }

}

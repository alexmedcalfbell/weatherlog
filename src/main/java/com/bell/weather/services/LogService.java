package com.bell.weather.services;

import com.bell.weather.models.Log;
import com.bell.weather.models.LogData;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for reading and manipulating log files.
 *
 * @author Alexander Medcalf-Bell
 */
@Service
public class LogService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private final static String ACCEPTED_EXTENSION = ".log";
    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final CsvMapper csvMapper = new CsvMapper();

    @Value("${log.directory.path}")
    private String logDirectory;

    /**
     * Read .log files in the provided directory
     *
     * @return List of {@link Log}
     */
    public List<Log> getLogs() {
        LOGGER.info("Reading logs at path [{}]", logDirectory);

        try (final Stream<Path> paths = Files.walk(Paths.get(logDirectory))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(log -> log.toFile().isFile())
                    .filter(log -> log.toFile().getName().endsWith(ACCEPTED_EXTENSION))
                    .map(log -> new Log()
                            .setName(log.getFileName().toString())
                            .setPath(log.toAbsolutePath().toString())
                            .setSize(FileUtils.byteCountToDisplaySize(log.toFile().length()))
                            .setModified(new SimpleDateFormat(DATE_FORMAT).format(log.toFile().lastModified()))
                    )
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            return Collections.emptyList();
        }
    }


    //TODO: The provided CSV files don't have commas after motion....why?

    /**
     * Read the log file at the specified path, parsing CSV into Log object.
     *
     * @param logPath path of the log file
     * @return List of {@link LogData}
     */
    public List<LogData> readLog(final String logPath) {

        LOGGER.info("Reading log at path [{}]", logPath);
        try {
            final CsvSchema csvSchema = CsvSchema.builder()
                    .addColumn("light")
                    .addColumn("rgb")
                    .addColumn("motion")
                    .addColumn("heading")
                    .addColumn("temperature")
                    .addColumn("pressure")
                    .setSkipFirstDataRow(true)
                    .build();

            final MappingIterator<LogData> readValues = csvMapper
                    .readerFor(LogData.class)
                    .with(csvSchema)
                    .readValues(new File(logPath));

            return readValues.readAll();
        } catch (final Exception e) {
            LOGGER.error("Failed to read log at path [{}]", logPath, e);
            return Collections.emptyList();
        }
    }
}

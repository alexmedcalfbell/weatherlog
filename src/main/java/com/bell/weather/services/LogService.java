package com.bell.weather.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bell.weather.models.Log;
import com.bell.weather.models.LogOld;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Service for reading and manipulating log files.
 * @author Alexander Medcalf-Bell
 */
@Service
public class LogService {

	private final static Logger LOGGER = LoggerFactory.getLogger(LogService.class);
	private final FileSystem fileSystem = FileSystems.getDefault();

	//    @Value("${log.path}")
	//    private String logPath;

	@Value("${log.directory.path}")
	private String logDirectory;

	public LogOld readLog(final String logPath) throws IOException {
		LOGGER.info("Reading log at path [{}]", logPath);

		LOGGER.info("Empty [{}]",readLogFile(logPath).isEmpty());
//		LOGGER.info("Light[{}]",readLogFile(logPath).get(0).getLight());

		final Path path = fileSystem.getPath(logPath);
		try (Stream<String> lines = Files.lines(path)) {
			return new LogOld().setData(lines.collect(Collectors.joining("<br>"))); //TODO: this should read into a list as it'll be a csv. No need for BR
		}
	}

	//    public List<Log> getLogPaths() throws IOException {
	//        //final Path logDirectory = fileSystem.getPath(logDirectory)
	//        try (final Stream<Path> paths = Files.walk(Paths.get(this.logDirectory))) {
	//            return paths.filter(Files::isRegularFile)
	//                    .map(p -> new Log().setData()p.toAbsolutePath().toString()).collect(
	//                    Collectors.toList());
	//        }
	//    }

	public List<LogOld> getLogs() throws IOException {
		LOGGER.info("Reading logs at path [{}]", logDirectory);
		//final Path logDirectory = fileSystem.getPath(logDirectory)
		try (Stream<Path> paths = Files.walk(Paths.get(logDirectory))) {
			return paths
					.filter(Files::isRegularFile)
					.filter(log -> log.toFile().isFile())
					.filter(log -> log.toFile().getName().endsWith(".log"))
					.map(log -> new LogOld()
							.setName(log.getFileName().toString())
							.setPath(log.toAbsolutePath().toString())
							.setSize(FileUtils.byteCountToDisplaySize(log.toFile().length()))
						 	.setModified(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(log.toFile().lastModified()))
					)
					.collect(Collectors.toList());
		}
	}

	//TODO: Get the csvmapper working
	public List<Log> readLogFile(String logPath) {
		try {
			final CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
			final CsvMapper csvMapper = new CsvMapper();

			LOGGER.info("Reading log at path [{}]", logPath);


			final File file = new ClassPathResource(logPath).getFile();

			MappingIterator<Log> readValues = csvMapper
					.readerFor(Log.class)
					.with(csvSchema)
					.readValues(file);

			return readValues.readAll();
		} catch (Exception e) {
			LOGGER.error("Error occurred while loading object list from file " + logPath, e);
			return Collections.emptyList();
		}
	}
}

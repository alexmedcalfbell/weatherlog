package com.bell.weather.services;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bell.weather.models.Log;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
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

	public Log readLog(final String logPath) throws IOException {
		LOGGER.info("Reading log at path [{}]", logPath);

		final Path path = fileSystem.getPath(logPath);
		try (Stream<String> lines = Files.lines(path)) {
			return new Log().setData(lines.collect(Collectors.joining("<br>"))); //TODO: this should read into a list as it'll be a csv. No need for BR
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

	public List<Log> getLogs() throws IOException {
		LOGGER.info("Reading logs at path [{}]", logDirectory);
		//final Path logDirectory = fileSystem.getPath(logDirectory)
		try (Stream<Path> paths = Files.walk(Paths.get(logDirectory))) {
			return paths
					.filter(Files::isRegularFile)
					.filter(log -> log.toFile().isFile())
					.filter(log -> log.toFile().getName().endsWith(".csv"))
					.map(log -> new Log()
							.setName(log.getFileName().toString())
							.setPath(log.toAbsolutePath().toString())
							.setSize(FileUtils.byteCountToDisplaySize(log.toFile().length()))
						 	.setModified(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(log.toFile().lastModified()))
					)
					.collect(Collectors.toList());
		}
	}
	//TODO: Ability to read multiple files in a directory and return the list to the controller which could potentially be displayed in cards
	// or just loaded into a box etc
}

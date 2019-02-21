package com.bell.weather.models;

/**
 * Models a log file.
 * @author Alexander Medcalf-Bell
 */
public class Log {

	private String data;

	private String path;

	private String size;

	private String name;

	public String getPath() {
		return path;
	}

	public Log setPath(final String path) {
		this.path = path;
		return this;
	}

	public String getSize() {
		return size;
	}

	public Log setSize(final String size) {
		this.size = size;
		return this;
	}

	public String getName() {
		return name;
	}

	public Log setName(final String name) {
		this.name = name;
		return this;
	}

	public String getData() {
		return this.data;
	}

	public Log setData(final String data) {
		this.data = data;
		return this;
	}
}

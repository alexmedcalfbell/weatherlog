package com.bell.weather.models;

/**
 * Models a log file.
 * @author Alexander Medcalf-Bell
 */
public class LogOld {

	private String data;

	private String path;

	private String size;

	private String name;

	private String modified;

	public String getPath() {
		return path;
	}

	public LogOld setPath(final String path) {
		this.path = path;
		return this;
	}

	public String getSize() {
		return size;
	}

	public LogOld setSize(final String size) {
		this.size = size;
		return this;
	}

	public String getName() {
		return name;
	}

	public LogOld setName(final String name) {
		this.name = name;
		return this;
	}

	public String getData() {
		return this.data;
	}

	public LogOld setData(final String data) {
		this.data = data;
		return this;
	}

	public String getModified() {
		return modified;
	}

	public LogOld setModified(String modified) {
		this.modified = modified;
		return this;
	}
}

package com.bell.weather.models;

public class Log {

    private String data;

    private String path;

    private long size;

    private String name;

    public String getPath() {
        return path;
    }

    public Log setPath(final String path) {
        this.path = path;
        return this;
    }

    public long getSize() {
        return size;
    }

    public Log setSize(final long size) {
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

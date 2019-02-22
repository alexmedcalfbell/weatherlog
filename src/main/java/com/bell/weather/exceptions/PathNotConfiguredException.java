package com.bell.weather.exceptions;

public class PathNotConfiguredException extends RuntimeException {

    public PathNotConfiguredException() {
    }

    public PathNotConfiguredException(final String message) {
        super(message);
    }

    public PathNotConfiguredException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

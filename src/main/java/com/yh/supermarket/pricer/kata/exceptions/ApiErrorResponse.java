package com.yh.supermarket.pricer.kata.exceptions;

import java.time.LocalDateTime;


public class ApiErrorResponse {
    private final String errorCode;
    private final String message;
    private final Integer statusCode;
    private final String statusName;
    private final String path;
    private final String method;
    private final LocalDateTime timestamp;

    public ApiErrorResponse(String errorCode, String message, Integer statusCode, String statusName, String path, String method, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.path = path;
        this.method = method;
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

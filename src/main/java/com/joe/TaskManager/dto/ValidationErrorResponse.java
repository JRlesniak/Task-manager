package com.joe.TaskManager.dto;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {
    public ValidationErrorResponse(LocalDateTime timeStamp, int status, String message, String
            request, Map<String, String> errors) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
        this.request = request;
        this.errors = errors;
    }

    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private String request;
    private Map<String,String> errors;
}

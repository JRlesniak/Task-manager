package com.joe.TaskManager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDTO {
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String path;

    public ErrorResponseDTO(LocalDateTime timeStamp, int status, String error, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }
}

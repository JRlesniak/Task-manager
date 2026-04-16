package com.joe.TaskManager.exception;

public class InvalidDueDateException extends RuntimeException{

    public InvalidDueDateException(String message){
        super(message);
    }
}

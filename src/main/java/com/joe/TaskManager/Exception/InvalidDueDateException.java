package com.joe.TaskManager.Exception;

public class InvalidDueDateException extends RuntimeException{

    public InvalidDueDateException(String message){
        super(message);
    }
}

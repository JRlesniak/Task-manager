package com.joe.TaskManager.exception;

public class TaskNotFoundException extends NullPointerException{
    public TaskNotFoundException(String message){
        super(message);
    }
}

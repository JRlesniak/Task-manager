package com.joe.TaskManager.Exception;

public class TaskNotFoundException extends NullPointerException{
    public TaskNotFoundException(String message){
        super(message);
    }
}

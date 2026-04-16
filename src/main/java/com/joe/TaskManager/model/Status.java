package com.joe.TaskManager.model;

public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

    public boolean isComplete(){
        return this == DONE;
    }
}

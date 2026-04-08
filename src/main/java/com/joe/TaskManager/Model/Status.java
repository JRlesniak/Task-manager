package com.joe.TaskManager.Model;

public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

    public boolean isComplete(){
        return this == DONE;
    }
}

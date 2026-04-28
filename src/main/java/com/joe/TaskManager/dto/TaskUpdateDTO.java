package com.joe.TaskManager.dto;

import com.joe.TaskManager.model.Status;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskUpdateDTO {

    private String title;
    private String description;
    private Status status;

    @FutureOrPresent(message = "Due Date cannot be in the past")
    private LocalDateTime dueDate;
}

package com.joe.TaskManager.dto;


import com.joe.TaskManager.model.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

}

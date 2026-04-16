package com.joe.TaskManager.service;

import com.joe.TaskManager.exception.InvalidDueDateException;
import com.joe.TaskManager.exception.TaskNotFoundException;
import com.joe.TaskManager.model.Status;
import com.joe.TaskManager.model.Task;
import com.joe.TaskManager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository
                .findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task createTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    public Task fullUpdateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setTitle(updatedTask.getTitle());
        if (updatedTask.getDueDate().isBefore(LocalDate.now())) {
            throw new InvalidDueDateException("Due date cannot be in the past");
        }
        existingTask.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(existingTask);
    }

    public Task partialUpdateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getDueDate() != null) {
            if (updatedTask.getDueDate().isBefore(LocalDate.now())) {
                throw new InvalidDueDateException("Due date cannot be in the past");
            }
            existingTask.setDueDate(updatedTask.getDueDate());
        }
        return taskRepository.save(existingTask);
    }

    public List<Task> getTaskByStatus(Status status) {
        return taskRepository.getTaskByStatus(status);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), Status.DONE);
    }

}

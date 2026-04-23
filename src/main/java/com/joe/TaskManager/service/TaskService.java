package com.joe.TaskManager.service;

import com.joe.TaskManager.dto.TaskRequestDTO;
import com.joe.TaskManager.dto.TaskResponseDTO;
import com.joe.TaskManager.exception.InvalidDueDateException;
import com.joe.TaskManager.exception.TaskNotFoundException;
import com.joe.TaskManager.model.Status;
import com.joe.TaskManager.model.Task;
import com.joe.TaskManager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    public TaskResponseDTO getTaskById(Long id) {
        return taskRepository
                .findById(id).map(this::mapToDTO).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public TaskResponseDTO createTask(@Valid TaskRequestDTO taskDTO) {
        Task task = mapToEntity(taskDTO);
        taskRepository.save(task);
        return mapToDTO(task);
    }

    public TaskResponseDTO fullUpdateTask(Long id, @Valid TaskRequestDTO updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setTitle(updatedTask.getTitle());
        if (updatedTask.getDueDate().isBefore(LocalDate.now())) {
            throw new InvalidDueDateException("Due date cannot be in the past");
        }
        existingTask.setDueDate(updatedTask.getDueDate());

        return mapToDTO(taskRepository.save(existingTask));
    }

    public TaskResponseDTO partialUpdateTask(Long id, TaskRequestDTO updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

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
        return mapToDTO(taskRepository.save(existingTask));
    }

    public List<TaskResponseDTO> findByStatus(Status status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), Status.DONE);
    }

    private TaskResponseDTO mapToDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate()
        );
    }

    private Task mapToEntity(TaskRequestDTO dto) {
        return new Task(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getDueDate()
        );
    }

}

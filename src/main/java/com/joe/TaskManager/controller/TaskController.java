package com.joe.TaskManager.controller;

import com.joe.TaskManager.dto.TaskRequestDTO;
import com.joe.TaskManager.model.Status;
import com.joe.TaskManager.dto.TaskResponseDTO;
import com.joe.TaskManager.model.Task;
import com.joe.TaskManager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @PageableDefault(size = 5, sort = "dueDate") Pageable pageable)  {
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTaskByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(taskService.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO task) {
        TaskResponseDTO savedTask = taskService.createTask(task);
        URI location = URI.create("/tasks/" + savedTask.getId());
        return ResponseEntity.created(location).body(savedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/overdue")
    public  ResponseEntity<List<Task>> getOverdueTask() {
        List<Task> task = taskService.getOverdueTasks();
        if (task.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> fullUpdateTask(
            @PathVariable Long id, @RequestBody @Valid TaskRequestDTO task) {
        TaskResponseDTO updated = taskService.fullUpdateTask(id, task);
        return ResponseEntity.ok(updated);
    }

}

package com.joe.TaskManager.Controller;

import com.joe.TaskManager.Model.Status;
import com.joe.TaskManager.Model.Task;
import com.joe.TaskManager.Service.TaskService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTaskByStatus(@PathVariable Status status) {
        List<Task> task = taskService.getTaskByStatus(status);
        if (task.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        URI location = URI.create("/tasks/" + savedTask.getId());
        return ResponseEntity.created(location).body(savedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
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
    public ResponseEntity<Task> fullUpdateTask(@PathVariable Long id, @RequestBody @Valid Task task) {
        Task updated = taskService.fullUpdateTask(id, task);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")// partially updates task
    public ResponseEntity<Task> partialUpdateTask(
            @PathVariable Long id,
            @RequestBody Task task) {
        taskService.partialUpdateTask(id, task);
        Task updated = taskService.getTaskById(id);
        return ResponseEntity.ok(updated);
    }
}

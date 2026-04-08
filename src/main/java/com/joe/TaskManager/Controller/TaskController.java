package com.joe.TaskManager.Controller;

import com.joe.TaskManager.Model.Status;
import com.joe.TaskManager.Model.Task;
import com.joe.TaskManager.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTaskByStatus(@PathVariable Status status) {return taskService.getTaskByStatus(status);}

    @PostMapping
    public void creatTask(@RequestBody @Valid Task task){
        taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable @Valid Long id, @RequestBody Task task){
        taskService.updateTask(id , task);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

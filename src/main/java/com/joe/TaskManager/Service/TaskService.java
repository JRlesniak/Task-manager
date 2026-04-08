package com.joe.TaskManager.Service;

import com.joe.TaskManager.Model.Status;
import com.joe.TaskManager.Model.Task;
import com.joe.TaskManager.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){return taskRepository.findAll();    }

    public Task getTaskById(Long id){return taskRepository
            .findById(id).orElseThrow(() -> new RuntimeException("Task not found")); }

    public Task createTask(Task task){
        validateDueDate(task);
        return taskRepository.save(task);}

    public Task updateTask(Long id, Task updatedTask){
         Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
         validateDueDate(updatedTask);
         existingTask.setTitle(updatedTask.getTitle());
         existingTask.setStatus(updatedTask.getStatus());
         existingTask.setDescription(updatedTask.getDescription());
         existingTask.setDueDate(updatedTask.getDueDate());
         return taskRepository.save(existingTask);
    }

    public List<Task> getTaskByStatus(Status status){
        return taskRepository.getTaskByStatus(status);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public void validateDueDate(Task task){
        if (task.getDueDate() == null) {
            throw new NullPointerException("Due date required");
        }
        if (task.getDueDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Due date cannot be in the past");
        }
    }

}

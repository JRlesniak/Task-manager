package com.joe.TaskManager.Service;

import com.joe.TaskManager.Exception.InvalidDueDateException;
import com.joe.TaskManager.Model.Status;
import com.joe.TaskManager.Model.Task;
import com.joe.TaskManager.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void createTask(Task task){
        taskRepository.save(task);}

    public void updateTask(Long id, Task updatedTask){
         Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

         if (existingTask.getTitle() != null ) existingTask.setTitle(updatedTask.getTitle());
         if (existingTask.getStatus() != null ) existingTask.setStatus(updatedTask.getStatus());

         existingTask.setDescription(updatedTask.getDescription());

         if(existingTask.getDueDate() != null){
             if (updatedTask.getDueDate().isBefore(LocalDate.now())) {
                 throw new InvalidDueDateException("Due date cannot be in the past");
             }
             existingTask.setDueDate(updatedTask.getDueDate());
         }

         taskRepository.save(existingTask);
    }

    public List<Task> getTaskByStatus(Status status){
        return taskRepository.getTaskByStatus(status);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }


}

package com.joe.TaskManager.Repository;

import com.joe.TaskManager.Model.Status;
import com.joe.TaskManager.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTaskByStatus(Status status);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, Status status);

}

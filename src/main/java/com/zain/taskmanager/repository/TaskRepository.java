package com.zain.taskmanager.repository;

import com.zain.taskmanager.entity.Task;
import com.zain.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
}

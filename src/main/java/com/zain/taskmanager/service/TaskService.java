package com.zain.taskmanager.service;

import com.zain.taskmanager.dto.CreateTaskRequest;
import com.zain.taskmanager.dto.TaskResponse;
import com.zain.taskmanager.entity.Task;
import com.zain.taskmanager.enums.TaskStatus;
import com.zain.taskmanager.exception.TaskNotFoundException;
import com.zain.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(CreateTaskRequest request) {
        log.info("Creating new task with title: {}", request.getTitle());
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO);
        Task saved = taskRepository.save(task);
        log.info("Task created with id: {}", saved.getId());
        return TaskResponse.from(saved);
    }

    public List<TaskResponse> getAllTasks() {
        log.info("Fetching all tasks");
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

    public Page<TaskResponse> getAllTasksPaged(Pageable pageable) {
        log.info("Fetching all tasks paged");
        return taskRepository.findAll(pageable).map(TaskResponse::from);
    }

    public TaskResponse getTaskById(Long id) {
        log.info("Fetching task with id: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskResponse.from(task);
    }

    public TaskResponse updateTask(Long id, CreateTaskRequest request) {
        log.info("Updating task with id: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        Task updated = taskRepository.save(task);
        log.info("Task updated: {}", id);
        return TaskResponse.from(updated);
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
        log.info("Task deleted: {}", id);
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        log.info("Fetching tasks with status: {}", status);
        return taskRepository.findByStatus(status)
                .stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

    public Page<TaskResponse> getTasksByStatusPaged(TaskStatus status, Pageable pageable) {
        log.info("Fetching tasks with status: {} paged", status);
        return taskRepository.findByStatus(status, pageable).map(TaskResponse::from);
    }
}

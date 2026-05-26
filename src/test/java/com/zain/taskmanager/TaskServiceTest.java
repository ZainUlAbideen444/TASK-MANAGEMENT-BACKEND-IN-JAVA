package com.zain.taskmanager;

import com.zain.taskmanager.dto.CreateTaskRequest;
import com.zain.taskmanager.dto.TaskResponse;
import com.zain.taskmanager.entity.Task;
import com.zain.taskmanager.enums.TaskStatus;
import com.zain.taskmanager.exception.TaskNotFoundException;
import com.zain.taskmanager.repository.TaskRepository;
import com.zain.taskmanager.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task buildTask(Long id, String title, TaskStatus status) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription("Test Description");
        task.setStatus(status);
        return task;
    }

    @Test
    @DisplayName("Create task - should return TaskResponse")
    void createTask_ShouldReturnTaskResponse() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.TODO);

        Task saved = buildTask(1L, "Test Task", TaskStatus.TODO);
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        assertEquals(TaskStatus.TODO, response.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Create task with null status - should default to TODO")
    void createTask_NullStatus_DefaultsToTodo() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("No Status Task");
        request.setStatus(null);

        Task saved = buildTask(2L, "No Status Task", TaskStatus.TODO);
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        TaskResponse response = taskService.createTask(request);
        assertEquals(TaskStatus.TODO, response.getStatus());
    }

    @Test
    @DisplayName("Get task by ID - found")
    void getTaskById_WhenFound_ShouldReturn() {
        Task task = buildTask(1L, "Found Task", TaskStatus.IN_PROGRESS);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Get task by ID - not found should throw")
    void getTaskById_WhenNotFound_ShouldThrow() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    @DisplayName("Get all tasks - should return list")
    void getAllTasks_ShouldReturnList() {
        when(taskRepository.findAll()).thenReturn(List.of(
                buildTask(1L, "Task 1", TaskStatus.TODO),
                buildTask(2L, "Task 2", TaskStatus.DONE)
        ));
        List<TaskResponse> result = taskService.getAllTasks();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Update task - should return updated")
    void updateTask_ShouldReturnUpdated() {
        Task existing = buildTask(1L, "Old Title", TaskStatus.TODO);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));

        Task updated = buildTask(1L, "New Title", TaskStatus.IN_PROGRESS);
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("New Title");
        request.setStatus(TaskStatus.IN_PROGRESS);

        TaskResponse response = taskService.updateTask(1L, request);
        assertEquals("New Title", response.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, response.getStatus());
    }

    @Test
    @DisplayName("Update task - not found should throw")
    void updateTask_WhenNotFound_ShouldThrow() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Any");
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(99L, request));
    }

    @Test
    @DisplayName("Delete task - should delete successfully")
    void deleteTask_WhenExists_ShouldDelete() {
        Task task = buildTask(1L, "Task to delete", TaskStatus.TODO);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Delete task - not found should throw")
    void deleteTask_WhenNotFound_ShouldThrow() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(99L));
    }

    @Test
    @DisplayName("Filter by status - should return filtered list")
    void getTasksByStatus_ShouldReturnFiltered() {
        when(taskRepository.findByStatus(TaskStatus.DONE)).thenReturn(List.of(
                buildTask(1L, "Done Task", TaskStatus.DONE)
        ));
        List<TaskResponse> result = taskService.getTasksByStatus(TaskStatus.DONE);
        assertEquals(1, result.size());
        assertEquals(TaskStatus.DONE, result.get(0).getStatus());
    }
}

package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.taskmanager.exception.TaskValidationException;
import com.taskmanager.exception.TaskNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        validateTask(task.getTitle(), task.getDescription());
        task.setStatus("PENDING");
        return taskRepository.save(task);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task with ID " + id + " not found."
                        ));
    }

    public Task updateTask(Long id, Task updatedTask) {
        validateTask(updatedTask.getTitle(), updatedTask.getDescription());
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    return taskRepository.save(task);
                })
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task with ID " + id + " not found."
                        ));
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(
                    "Task with ID " + id + " not found."
            );
        }
        taskRepository.deleteById(id);
        return true;
    }
    private void validateTask(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new TaskValidationException("Title cannot be empty.");
        }
        if (title.length() > 100) {
            throw new TaskValidationException(
                    "Title cannot exceed 100 characters."
            );
        }
        if (description == null || description.trim().isEmpty()) {
            throw new TaskValidationException(
                    "Description cannot be empty."
            );
        }
        if (description.length() > 500) {
            throw new TaskValidationException(
                    "Description cannot exceed 500 characters."
            );
        }
    }
    public Task updateStatus(Long id, String status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task with ID " + id + " not found."
                        ));
        if (!status.equals("PENDING")
                && !status.equals("IN_PROGRESS")
                && !status.equals("COMPLETED")) {

            throw new TaskValidationException(
                    "Invalid status. Use PENDING, IN_PROGRESS, or COMPLETED."
            );
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
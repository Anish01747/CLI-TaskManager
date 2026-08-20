package com.taskmanager.command;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.exception.TaskValidationException;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;

@Component
public class TaskCommand {

    private final TaskService taskService;

    public TaskCommand(TaskService taskService) {
        this.taskService = taskService;
    }
    @Command(name = "add", description = "Add a new task")
    public String addTask(
            @Argument(index = 0, description = "Task title")
            String title,

            @Argument(index = 1, description = "Task description")
            String description) {
        try {
            Task task = new Task(title, description);
            Task savedTask = taskService.addTask(task);

            return "Task added successfully: " + savedTask;
        } catch (TaskValidationException e) {
            return e.getMessage();
        }
    }

    @Command(name = "list", description = "List all tasks")
    public String listTasks() {

        return taskService.getAllTasks().toString();
    }
    @Command(name = "get", description = "Get task by ID")
    public String getTask(
            @Argument(index = 0, description = "Task ID")
            Long id) {
        try {
            return taskService.getTaskById(id).toString();

        } catch (TaskNotFoundException e) {
            return e.getMessage();
        }
    }

    @Command(name = "delete", description = "Delete a task")
    public String deleteTask(
            @Argument(index = 0, description = "Task ID")
            Long id) {
        try {
            taskService.deleteTask(id);
            return "Task deleted successfully.";
        } catch (TaskNotFoundException e) {
            return e.getMessage();
        }
    }

    @Command(name = "update", description = "Update an existing task")
    public String updateTask(
            @Argument(index = 0, description = "Task ID")
            Long id,
            @Argument(index = 1, description = "New task title")
            String title,
            @Argument(index = 2, description = "New task description")
            String description) {
        try {
            Task updatedTask = new Task(title, description);

            Task task = taskService.updateTask(id, updatedTask);

            return "Task updated successfully: " + task;
        } catch (TaskValidationException e) {
            return e.getMessage();
        } catch (TaskNotFoundException e) {
            return e.getMessage();
        }
    }
    @Command(name = "status", description = "Update task status")
    public String updateStatus(
            @Argument(index = 0, description = "Task ID")
            Long id,
            @Argument(index = 1, description = "New status")
            String status) {
        try {
            taskService.updateStatus(id, status.toUpperCase());
            return "Task status updated successfully.";
        } catch (TaskValidationException e) {
            return e.getMessage();
        } catch (TaskNotFoundException e) {
            return e.getMessage();
        }
    }
}

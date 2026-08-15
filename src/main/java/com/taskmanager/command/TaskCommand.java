package com.taskmanager.command;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;

import java.util.List;

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

        Task task = taskService.addTask(title, description);

        return "Task created: " + task;
    }

    @Command(name = "list", description = "List all tasks")
    public String listTasks() {

        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return "No tasks found.";
        }

        return tasks.toString();
    }

    @Command(name = "update", description = "Update a task")
    public String updateTask(
            @Argument(index = 0, description = "Task ID")
            int id,

            @Argument(index = 1, description = "New title")
            String title,

            @Argument(index = 2, description = "New description")
            String description) {

        boolean updated = taskService.updateTask(id, title, description);

        if (!updated) {
            return "Task not found with ID: " + id;
        }

        return "Task updated successfully.";
    }

    @Command(name = "delete", description = "Delete a task")
    public String deleteTask(
            @Argument(index = 0, description = "Task ID")
            int id) {

        boolean deleted = taskService.deleteTask(id);

        if (!deleted) {
            return "Task not found with ID: " + id;
        }

        return "Task deleted successfully.";
    }

    @Command(name = "complete", description = "Mark a task as completed")
    public String completeTask(
            @Argument(index = 0, description = "Task ID")
            int id) {

        boolean completed = taskService.completeTask(id);

        if (!completed) {
            return "Task not found with ID: " + id;
        }

        return "Task marked as completed.";
    }
}

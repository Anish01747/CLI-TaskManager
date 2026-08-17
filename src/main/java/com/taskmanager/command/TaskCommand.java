package com.taskmanager.command;

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

        Task task = new Task(title, description);
        Task savedTask = taskService.addTask(task);

        return "Task added successfully: " + savedTask;
    }

    @Command(name = "list", description = "List all tasks")
    public String listTasks() {

        return taskService.getAllTasks().toString();
    }

    @Command(name = "get", description = "Get task by ID")
    public String getTask(
            @Argument(index = 0, description = "Task ID")
            Long id) {

        return taskService.getTaskById(id)
                .map(Task::toString)
                .orElse("Task not found with ID: " + id);
    }

    @Command(name = "delete", description = "Delete task by ID")
    public String deleteTask(
            @Argument(index = 0, description = "Task ID")
            Long id) {

        if (taskService.deleteTask(id)) {
            return "Task deleted successfully.";
        }

        return "Task not found with ID: " + id;
    }
    @Command(name = "update", description = "Update an existing task")
    public String updateTask(
            @Argument(index = 0, description = "Task ID")
            Long id,

            @Argument(index = 1, description = "New task title")
            String title,

            @Argument(index = 2, description = "New task description")
            String description) {

        Task updatedTask = new Task(title, description);

        Task task = taskService.updateTask(id, updatedTask);

        if (task == null) {
            return "Task not found with ID: " + id;
        }

        return "Task updated successfully: " + task;
    }
}

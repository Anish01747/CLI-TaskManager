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
            @Argument(index=0,description = "Task title")
            String title,

            @Argument(index=1,description = "Task description")
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
}

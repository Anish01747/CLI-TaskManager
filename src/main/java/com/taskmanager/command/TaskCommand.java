package com.taskmanager.command;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.exception.TaskValidationException;
import java.util.List;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
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
            String description,
            @Argument(index = 2, description = "Due date (YYYY-MM-DD)")
            String dueDate) {
        try {
            Task task = new Task(title, description);
            task.setDueDate(LocalDate.parse(dueDate));
            Task savedTask = taskService.addTask(task);
            return "Task added successfully: " + savedTask;
        } catch (TaskValidationException e) {
            return e.getMessage();
        } catch (java.time.format.DateTimeParseException e) {
            return "Invalid date format. Use YYYY-MM-DD.";
        }
    }

    @Command(name = "list", description = "List all tasks")
    public String listTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "%-5s %-20s %-15s %-12s%n",
                "ID", "TITLE", "STATUS", "DUE DATE"
        ));
        result.append("----------------------------------------------------------\n");
        for (Task task : tasks) {
            result.append(String.format(
                    "%-5d %-20s %-15s %-12s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getDueDate() != null ? task.getDueDate() : "-"
            ));
        }
        return result.toString();
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
            String description,
            @Argument(index = 3, description = "New due date (YYYY-MM-DD)")
            String dueDate) {
        try {
            Task updatedTask = new Task(title, description);
            updatedTask.setDueDate(LocalDate.parse(dueDate));
            Task task = taskService.updateTask(id, updatedTask);
            return "Task updated successfully: " + task;
        } catch (TaskValidationException e) {
            return e.getMessage();
        } catch (TaskNotFoundException e) {
            return e.getMessage();
        } catch (java.time.format.DateTimeParseException e) {
            return "Invalid date format. Use YYYY-MM-DD.";
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
    @Command(name = "search", description = "Search tasks by keyword")
    public String searchTasks(
            @Argument(index = 0, description = "Search keyword")
            String keyword) {
        List<Task> tasks = taskService.searchTasks(keyword);
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "%-5s %-20s %-15s %-12s%n",
                "ID", "TITLE", "STATUS", "DUE DATE"
        ));
        result.append("----------------------------------------------------------\n");

        for (Task task : tasks) {
            result.append(String.format(
                    "%-5d %-20s %-15s %-12s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getDueDate() != null ? task.getDueDate() : "-"
            ));
        }
        return result.toString();
    }
    @Command(name = "filter", description = "Filter tasks by status")
    public String filterTasks(
            @Argument(index = 0, description = "Task status")
            String status) {
        List<Task> tasks = taskService.filterByStatus(status);
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "%-5s %-20s %-15s %-12s%n",
                "ID", "TITLE", "STATUS", "DUE DATE"
        ));
        result.append("----------------------------------------------------------\n");
        for (Task task : tasks) {
            result.append(String.format(
                    "%-5d %-20s %-15s %-12s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getDueDate() != null ? task.getDueDate() : "-"
            ));
        }
        return result.toString();
    }
    @Command(name = "sort", description = "Sort tasks")
    public String sortTasks(
            @Argument(index = 0, description = "Field to sort by")
            String field,
            @Argument(index = 1, description = "Direction: asc or desc")
            String direction) {
        try {
            List<Task> tasks = taskService.sortTasks(field, direction);
            if (tasks.isEmpty()) {
                return "No tasks found.";
            }
            StringBuilder result = new StringBuilder();
            result.append(String.format(
                    "%-5s %-20s %-15s %-12s%n",
                    "ID", "TITLE", "STATUS", "DUE DATE"
            ));
            result.append("----------------------------------------------------------\n");
            for (Task task : tasks) {
                result.append(String.format(
                        "%-5d %-20s %-15s %-12s%n",
                        task.getId(),
                        task.getTitle(),
                        task.getStatus(),
                        task.getDueDate() != null ? task.getDueDate() : "-"
                ));
            }
            return result.toString();
        } catch (Exception e) {
            return "Invalid sort field: " + field;
        }
    }
    @Command(name = "overdue", description = "Show overdue tasks")
    public String overdueTasks() {
        List<Task> tasks = taskService.getOverdueTasks();
        if (tasks.isEmpty()) {
            return "No overdue tasks found.";
        }
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "%-5s %-20s %-15s %-12s%n",
                "ID", "TITLE", "STATUS", "DUE DATE"
        ));
        result.append("----------------------------------------------------------\n");
        for (Task task : tasks) {
            result.append(String.format(
                    "%-5d %-20s %-15s %-12s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getDueDate() != null ? task.getDueDate() : "-"
            ));
        }
        return result.toString();
    }
    @Command(name = "stats", description = "Show task statistics")
    public String showStatistics() {
        return "\n========== TASK STATISTICS ==========\n"
                + taskService.getStatistics()
                + "\n=====================================";
    }

}

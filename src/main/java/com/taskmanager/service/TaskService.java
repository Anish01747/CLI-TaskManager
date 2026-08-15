package com.taskmanager.service;

import com.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    private int nextId = 1;

    public Task addTask(String title, String description) {

        Task task = new Task(nextId, title, description);

        tasks.add(task);

        nextId++;

        return task;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {

        for (Task task : tasks) {

            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }

    public boolean updateTask(int id, String title, String description) {

        Task task = getTaskById(id);

        if (task == null) {
            return false;
        }

        task.setTitle(title);
        task.setDescription(description);

        return true;
    }

    public boolean deleteTask(int id) {

        Task task = getTaskById(id);

        if (task == null) {
            return false;
        }

        tasks.remove(task);

        return true;
    }

    public boolean completeTask(int id) {

        Task task = getTaskById(id);

        if (task == null) {
            return false;
        }

        task.setCompleted(true);

        return true;
    }
}
package com.taskmanager.service;

import com.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    private Long nextId = 1L;

    public Task addTask(String title, String description) {

        Task task = new Task(
                nextId++,
                title,
                description
        );

        tasks.add(task);

        return task;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
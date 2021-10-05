package org.loko.task;

import java.util.Deque;
import java.util.LinkedList;

public class TaskQueue {
    private final Deque<Task> tasks;

    public TaskQueue() {
        tasks = new LinkedList<>();
    }

    public TaskQueue(Deque<Task> tasks) {
        this.tasks = tasks;
    }

    public synchronized Task pollTask() {
        return tasks.pollFirst();
    }

    public synchronized void addTask(Task task) {
        tasks.addLast(task);
    }

    public synchronized boolean isNotEmpty() {
        return !tasks.isEmpty();
    }
}

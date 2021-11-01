package org.loko.task;

import java.util.Deque;
import java.util.LinkedList;

public class SimpleTaskQueue implements TaskQueue {
    private final Deque<Task> tasks;

    public SimpleTaskQueue() {
        tasks = new LinkedList<>();
    }

    public SimpleTaskQueue(Deque<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Task pollTask() {
        return tasks.pollFirst();
    }

    @Override
    public void addTask(Task task) {
        tasks.addLast(task);
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}

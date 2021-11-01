package org.loko.task;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CompositeTaskQueue implements TaskQueue {
    private final List<TaskQueue> taskQueues;

    public CompositeTaskQueue() {
        taskQueues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            taskQueues.add(new SimpleTaskQueue());
        }
    }

    public CompositeTaskQueue(Deque<Task> tasks) {
        this();
        for (Task task: tasks) {
            this.addTask(task);
        }
    }

    @Override
    public synchronized Task pollTask() {
        for (TaskQueue taskQueue: taskQueues) {
            if (!taskQueue.isEmpty()) {
                return taskQueue.pollTask();
            }
        }
        return null;
    }

    @Override
    public synchronized void addTask(Task task) {
        taskQueues.get(task.getPriority()).addTask(task);
    }

    @Override
    public synchronized boolean isEmpty() {
        return taskQueues.stream().allMatch(TaskQueue::isEmpty);
    }
}

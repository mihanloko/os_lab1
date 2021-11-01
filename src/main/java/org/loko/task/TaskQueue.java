package org.loko.task;

public interface TaskQueue {
    Task pollTask();
    void addTask(Task task);
    boolean isEmpty();
}

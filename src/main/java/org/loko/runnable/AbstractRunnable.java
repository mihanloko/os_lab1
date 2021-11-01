package org.loko.runnable;

import lombok.RequiredArgsConstructor;
import org.loko.task.Operation;
import org.loko.task.Task;
import org.loko.task.TaskQueue;

@RequiredArgsConstructor
abstract class AbstractRunnable implements Runnable {
    protected final TaskQueue processorTaskQueue;
    protected final TaskQueue ioTaskQueue;
    private final int quantum;
    private int previousRemind = 0;

    protected void processTask(Operation currentOperation, Task currentTask, TaskQueue ownQueue, TaskQueue otherQueue) throws InterruptedException {
        int timeToSleep = currentOperation.increaseExecutingTime(quantum + previousRemind);
        Thread.sleep(timeToSleep);
        previousRemind = previousRemind + quantum - timeToSleep;
        if (!currentTask.finishOperation()) {
            ownQueue.addTask(currentTask);
        } else if (currentTask.getCurrentOperation() != null) {
            otherQueue.addTask(currentTask);
        }
    }
}

package org.loko.runnable;

import lombok.SneakyThrows;
import org.loko.task.Operation;
import org.loko.task.OperationType;
import org.loko.task.Task;
import org.loko.task.TaskQueue;

public class IORunnable extends AbstractRunnable {

    public IORunnable(TaskQueue processorTaskQueue, TaskQueue ioTaskQueue, int quantum) {
        super(processorTaskQueue, ioTaskQueue, quantum);
    }

    @Override
    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            Task currentTask = ioTaskQueue.pollTask();
            if (currentTask == null) {
                continue;
            }
            Operation currentOperation = currentTask.getCurrentOperation();
            if (currentOperation == null) {
                break;
            }
            if (currentOperation.getType() == OperationType.CALCULATION) {
                processorTaskQueue.addTask(currentTask);
                break;
            }
            if (currentOperation.getType() == OperationType.IO) {
                processTask(currentOperation, currentTask, ioTaskQueue, processorTaskQueue);
            }
        }
    }
}

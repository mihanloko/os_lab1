package org.loko.runnable;

import lombok.SneakyThrows;
import org.loko.task.Operation;
import org.loko.task.OperationType;
import org.loko.task.Task;
import org.loko.task.TaskQueue;


public class ProcessorRunnable extends AbstractRunnable {

    public ProcessorRunnable(TaskQueue processorTaskQueue, TaskQueue ioTaskQueue, int quantum) {
        super(processorTaskQueue, ioTaskQueue, quantum);
    }

    @Override
    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            Task currentTask = processorTaskQueue.pollTask();
            if (currentTask == null) {
                continue;
            }
            Operation currentOperation = currentTask.getCurrentOperation();
            if (currentOperation == null) {
                break;
            }
            if (currentOperation.getType() == OperationType.IO) {
                ioTaskQueue.addTask(currentTask);
                break;
            }
            if (currentOperation.getType() == OperationType.CALCULATION) {
                processTask(currentOperation, currentTask, processorTaskQueue, ioTaskQueue);
            }
        }
    }
}

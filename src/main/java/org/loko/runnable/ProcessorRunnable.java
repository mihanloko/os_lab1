package org.loko.runnable;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.loko.task.TaskQueue;
import org.loko.task.Operation;
import org.loko.task.OperationType;
import org.loko.task.Task;

@AllArgsConstructor
public class ProcessorRunnable implements Runnable {
    private final TaskQueue processorTaskQueue;
    private final TaskQueue ioTaskQueue;

    @Override
    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            Task currentTask = processorTaskQueue.pollTask();
            if (currentTask == null) {
                continue;
            }
            while (true) {
                Operation currentOperation = currentTask.getCurrentOperation();
                if (currentOperation == null) {
                    break;
                }
                if (currentOperation.getType() == OperationType.IO) {
                    ioTaskQueue.addTask(currentTask);
                    break;
                }
                if (currentOperation.getType() == OperationType.CALCULATION) {
                    Thread.sleep(currentOperation.getDuration());
                    currentTask.finishOperation();
                }
            }
        }
    }
}

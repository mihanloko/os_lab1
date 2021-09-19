package org.loko.runnable;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.loko.task.TaskQueue;
import org.loko.task.Operation;
import org.loko.task.OperationType;
import org.loko.task.Task;

@AllArgsConstructor
public class IORunnable implements Runnable {
    private final TaskQueue ioTaskQueue;
    private final TaskQueue processorTaskQueue;

    @Override
    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            Task currentTask = ioTaskQueue.pollTask();
            if (currentTask == null) {
                continue;
            }
            while (true) {
                Operation currentOperation = currentTask.getCurrentOperation();
                if (currentOperation == null) {
                    break;
                }
                if (currentOperation.getType() == OperationType.CALCULATION) {
                    processorTaskQueue.addTask(currentTask);
                    break;
                }
                if (currentOperation.getType() == OperationType.IO) {
                    Thread.sleep(currentOperation.getDuration());
                    currentTask.finishOperation();
                }
            }
        }
    }
}

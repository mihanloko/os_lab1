package org.loko.task;

import java.util.List;

public class Task {
    private final List<Operation> operations;
    private int currentOperation;

    public Task(List<Operation> operations) {
        this.operations = operations;
        currentOperation = 0;
    }

    public synchronized Operation getCurrentOperation() {
        if (currentOperation < operations.size()) {
            return operations.get(currentOperation);
        }
        return null;
    }

    public void finishOperation() {
        currentOperation++;
        if (currentOperation == operations.size()) {
            //todo log
        }
    }
}

package org.loko.task;

import lombok.Getter;

import java.util.List;

public class Task {
    @Getter
    private final List<Operation> operations;
    private int currentOperation;
    @Getter
    private long startTime;
    @Getter
    private long finishTime;

    public Task(List<Operation> operations) {
        this.operations = operations;
        currentOperation = 0;
        startTime = 0;
    }

    public synchronized Operation getCurrentOperation() {
        if (currentOperation == 0 && startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        if (currentOperation < operations.size()) {
            return operations.get(currentOperation);
        }
        return null;
    }

    public synchronized Integer getCurrentOperationInd() {

        if (currentOperation < operations.size()) {
            return currentOperation;
        }
        return null;
    }

    public void finishOperation() {
        currentOperation++;
        if (currentOperation == operations.size()) {
            finishTime = System.currentTimeMillis();
        }
    }
}

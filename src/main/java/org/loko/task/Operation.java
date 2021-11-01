package org.loko.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Operation {
    private final OperationType type;
    private final int duration;
    private int executingTime = 0;

    public int increaseExecutingTime(int delta) {
        executingTime += delta;
        int timeToSleep = delta;
        if (executingTime > duration) {
            timeToSleep -= executingTime - duration;
            executingTime = duration;
        }
        return timeToSleep;
    }

    public boolean isFinished() {
        return executingTime >= duration;
    }
}


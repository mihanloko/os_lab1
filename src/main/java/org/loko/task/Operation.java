package org.loko.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Operation {
    private final OperationType type;
    private final int duration;
}


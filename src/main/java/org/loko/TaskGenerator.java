package org.loko;

import org.loko.task.Operation;
import org.loko.task.OperationType;
import org.loko.task.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TaskGenerator {

    public static LinkedList<Task> generate(int amount,
                                            int minCalculationDuration,
                                            int maxCalculationDuration,
                                            int minIODuration,
                                            int maxIODuration,
                                            int minSteps,
                                            int maxSteps) {
        LinkedList<Task> result = new LinkedList<>();
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            List<Operation> operations = new LinkedList<>();
            int delta = random.nextInt(maxSteps - minSteps);
            for (int j = 0; j < minSteps + delta; j++) {
                if (j % 2 == 0) {
                    int duration = minCalculationDuration + random.nextInt(maxCalculationDuration - minCalculationDuration);
                    operations.add(new Operation(OperationType.CALCULATION, duration));
                } else {
                    int duration = minIODuration + random.nextInt(maxIODuration - minIODuration);
                    operations.add(new Operation(OperationType.IO, duration));
                }
            }

            result.add(new Task(operations));
        }

        return result;
    }

}

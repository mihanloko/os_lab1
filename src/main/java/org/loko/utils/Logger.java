package org.loko.utils;

import lombok.AllArgsConstructor;
import org.loko.task.Operation;
import org.loko.task.Task;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Logger {

    public static void log(List<Task> tasks, String fileName) {
        List<Long> duration = new ArrayList<>(tasks.size());
        List<Long> processing = new ArrayList<>(tasks.size());
        List<Integer> size = new ArrayList<>(tasks.size());
        try (FileOutputStream fos = new FileOutputStream(fileName);
             DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            for (Task task : tasks) {
                outStream.writeBytes(String.format("%d %d %d\n",
                        task.getFinishTime() - task.getStartTime(),
                        calculateSum(task),
                        task.getOperations().size()));
                duration.add(task.getFinishTime() - task.getStartTime());
                processing.add(calculateSum(task));
                size.add(task.getOperations().size());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("duration: max - %d min %d%n", max(duration), min(duration));
        System.out.printf("processing: max - %d min %d%n", max(processing), min(processing));
        System.out.printf("size: max - %d min %d%n", max(size), min(size));
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < duration.size(); i++) {
            sum1 += processing.get(i);
            sum2 += duration.get(i);
        }
        System.out.printf("average processing/duration: %f%n", sum1 / sum2);

    }

    private static long calculateSum(Task task) {
        return task.getOperations().stream().mapToLong(Operation::getDuration).sum();
    }

    private static <E extends Number> long min(List<E> data) {
        return data.stream().mapToLong(Number::longValue).min().orElse(0);
    }

    private static <E extends Number> long max(List<E> data) {
        return data.stream().mapToLong(Number::longValue).max().orElse(0);
    }

}

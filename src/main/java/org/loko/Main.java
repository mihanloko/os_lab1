package org.loko;

import org.loko.runnable.IORunnable;
import org.loko.runnable.ProcessorRunnable;
import org.loko.task.Task;
import org.loko.task.TaskQueue;
import org.loko.utils.Logger;
import org.loko.utils.TaskGenerator;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        System.out.println("start " + LocalTime.now());
    //amount=100;minCalcDuration=100;maxCalcDuration=1000;minIODuration=200;maxIODuration=400;minSteps=2;maxSteps=5
        int amount = Integer.parseInt(System.getenv("amount"));
        int minCalcDuration = Integer.parseInt(System.getenv("minCalcDuration"));
        int maxCalcDuration = Integer.parseInt(System.getenv("maxCalcDuration"));
        int minIODuration = Integer.parseInt(System.getenv("minIODuration"));
        int maxIODuration = Integer.parseInt(System.getenv("maxIODuration"));
        int minSteps = Integer.parseInt(System.getenv("minSteps"));
        int maxSteps = Integer.parseInt(System.getenv("maxSteps"));

        LinkedList<Task> tasks = TaskGenerator.generate(amount,
                minCalcDuration,
                maxCalcDuration,
                minIODuration,
                maxIODuration,
                minSteps,
                maxSteps);

        LinkedList<Task> copy = new LinkedList<>(tasks);
        TaskQueue processorQueue = new TaskQueue(copy);
        TaskQueue ioQueue = new TaskQueue();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> processor = executor.submit(new ProcessorRunnable(processorQueue, ioQueue));
        Future<?> io = executor.submit(new IORunnable(ioQueue, processorQueue));

        while (!tasks.stream().map(Task::getCurrentOperation).allMatch(Objects::isNull)) {

        }

        processor.cancel(false);
        io.cancel(false);
        executor.shutdown();
        Logger.getExecutorService().shutdown();
        System.out.println("finish " + LocalTime.now());

    }
}

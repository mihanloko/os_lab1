package org.loko;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.loko.runnable.IORunnable;
import org.loko.runnable.ProcessorRunnable;
import org.loko.task.*;
import org.loko.utils.Logger;
import org.loko.utils.TaskGenerator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println("start " + LocalTime.now());

        //amount=100;minCalcDuration=100;maxCalcDuration=1000;minIODuration=200;maxIODuration=400;minSteps=2;maxSteps=5;quantum=10

        int amount = Integer.parseInt(System.getenv("amount"));
        int minCalcDuration = Integer.parseInt(System.getenv("minCalcDuration"));
        int maxCalcDuration = Integer.parseInt(System.getenv("maxCalcDuration"));
        int minIODuration = Integer.parseInt(System.getenv("minIODuration"));
        int maxIODuration = Integer.parseInt(System.getenv("maxIODuration"));
        int minSteps = Integer.parseInt(System.getenv("minSteps"));
        int maxSteps = Integer.parseInt(System.getenv("maxSteps"));
        int quantum = Integer.parseInt(System.getenv("quantum"));

        LinkedList<Task> tasks = TaskGenerator.generate(amount,
                minCalcDuration,
                maxCalcDuration,
                minIODuration,
                maxIODuration,
                minSteps,
                maxSteps);

        LinkedList<Task> copy = new LinkedList<>(tasks);
        TaskQueue processorQueue = new CompositeTaskQueue(copy);
        TaskQueue ioQueue = new CompositeTaskQueue();

        ExecutorService executor = Executors.newFixedThreadPool(2,
                new ThreadFactoryBuilder().setNameFormat("my-sad-thread-%d").build());
        Future<?> processor = executor.submit(new ProcessorRunnable(processorQueue, ioQueue, quantum));
        Future<?> io = executor.submit(new IORunnable(processorQueue, ioQueue, quantum));

        List<Task> check = new ArrayList<>(tasks);
        while (!check.isEmpty()) {
            check = check.stream().filter(t -> Objects.nonNull(t.getCurrentOperationInd())).collect(Collectors.toList());
            System.out.println(check.size());
            try {
                if (!check.isEmpty()) {
                    Thread.sleep(10000);
                }
            } catch (InterruptedException ignored) {
            }
        }

        processor.cancel(true);
        io.cancel(true);
        executor.shutdown();

        Logger.log(tasks, "result.txt");

        System.out.println("finish " + LocalTime.now());
    }
}

// лаб 2
// добавить диспетчеризацию
// режим таймшаринг

// сколько время провела в система
// среднее время выполнения активного

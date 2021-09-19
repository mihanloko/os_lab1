package org.loko.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class Logger {
    @Getter
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void log(long time) {
        executorService.submit(() -> {
            System.out.println(time);
        });
    }
}

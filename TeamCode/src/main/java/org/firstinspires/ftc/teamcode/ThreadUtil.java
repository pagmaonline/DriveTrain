package org.firstinspires.ftc.teamcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ThreadUtil {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    public static void runAsync(Runnable runnable) {
        executorService.submit(runnable);
    }

    public static void runOperations(Runnable[] runnables) {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }

    public static void sleep(int mills) {
        sleep(mills);
    }
}
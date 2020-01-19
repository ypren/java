package com.ypren.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleThreadScheduleTest {
    private static final Logger logger = LoggerFactory.getLogger(SingleThreadScheduleTest.class);
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    public void start() {
        Runnable task = new Task();
        scheduledExecutor.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        scheduledExecutor.scheduleAtFixedRate(new Task2(), 0, 5, TimeUnit.SECONDS);
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            logger.info("hello scheduledExecutor, {}", thread);
            Object a = null;
            a.toString();
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            logger.info("hello2 scheduledExecutor, {}", thread);
        }
    }

    public static void main(String[] args) {
        new SingleThreadScheduleTest().start();
    }
}

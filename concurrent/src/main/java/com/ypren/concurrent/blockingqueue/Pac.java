package com.ypren.concurrent.blockingqueue;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * This class will implement a producer and consumer via
 * {@link java.util.concurrent.BlockingQueue}.
 */
@Slf4j
public class Pac {
    private static final int DEFAULT_CAPACITY = 16;
    private static final BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<Task>(DEFAULT_CAPACITY);

    private static final ExecutorService consumerService = Executors.newSingleThreadExecutor();
    private static final ExecutorService producerService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        producerService.execute(new Producer());
        consumerService.execute(new Consumer());
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Task task = taskQueue.take();
                    log.info("consume task {}", task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2*1000);
                    taskQueue.put(new Task(UUID.randomUUID().toString()));
                } catch (InterruptedException e){
                    log.error("InterruptedException");
                }
            }
        }
    }

    private static class Task {
        private final String taskId;

        public Task(String taskId) {
            this.taskId = taskId;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "taskId='" + taskId + '\'' +
                    '}';
        }
    }
}

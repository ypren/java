package com.ypren.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5,
                new CustomizableThreadFactory("compute-task-"));

        List<Task> taskList = new ArrayList();
        for (long i = 0; i < 10; i++) {
            Task task = new Task(i);
            taskList.add(task);
        }

        for (Task task: taskList) {
            threadPool.submit(task);
        }

        threadPool.shutdown();
    }

    static class Task implements Runnable {
        long sleep;

        public Task(long sleep) {
            this.sleep = sleep;
        }

        @Override
        public void run() {
            try {
                log.info("task {} start", Thread.currentThread().getName());
                Thread.sleep(sleep * 1000);
                log.info("task {} end", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

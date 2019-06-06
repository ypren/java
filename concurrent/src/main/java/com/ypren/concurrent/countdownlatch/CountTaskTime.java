package com.ypren.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

/**
 * This class will use {@link java.util.concurrent.CountDownLatch}
 * to compute the time used by all tasks.
 */
@Slf4j
public class CountTaskTime {
    public static void main(String[] args) throws InterruptedException {
        final int nThread = 5;
        long time = timeTasks(nThread, new Task());
        log.info("task time: {}", time);
    }

    /**
     * Compute time waste by the special task.
     * @param nThreads the thread count to handle the task.
     * @param task task
     * @return seconds used.
     * @throws InterruptedException
     */
    private static long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0; i < nThreads; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        task.run();
                    } catch (InterruptedException ignore) {
                    } finally {
                        endGate.countDown();
                    }
                }
            };
            thread.setName("thread-"+i);
            thread.start();
        }

        long startTime = System.currentTimeMillis();
        startGate.countDown();
        log.info("task begin");
        endGate.await();
        log.info("---------------------");
        log.info("wait until all task finish");
        long endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2*1000);
                log.info("{} execute task", Thread.currentThread().getName());
            } catch (InterruptedException ignore) {
            }
        }
    }
}

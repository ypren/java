package com.ypren.demo.zookeeper.distributedlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

/**
 * Curator Distributed Lock test.
 */
@Slf4j
@Component
public class CuratorDistributedLock {

    private static ExecutorService executors = new ThreadPoolExecutor(
            3, 3, 60, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),
            new CustomizableThreadFactory("task-thread-pool"));

    public void test() {
        List<Future<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Task task = new Task();
            Future<Boolean> future = executors.submit(task);
            futureList.add(future);
        }

        log.info("wait for all task done.");
        // wait for all task ending.
        for (Future future: futureList) {
            Boolean value = false;
            while (!value) {
                try {
                    value = (Boolean) future.get();
                } catch (Exception e) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        log.info("all task done, shutdown thread pool.");
        executors.shutdown();
    }

    private static CuratorFramework newClient() {
        String connectString = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3*1000, 10);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        return client;
    }

    private static class Task implements Callable<Boolean> {
        private Counter counter = new Counter();

        @Override
        public Boolean call() {
            Thread thread = Thread.currentThread();
            log.info("thread: {}, try to get lock", thread.getName());
            CuratorFramework client = newClient();
            String lockPath = "/DISTRIBUTED_LOCK/UploadManager/FILE_UPLOAD/MASTER_LOCK";
            InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            client.getConnectionStateListenable().addListener((client1, newState) -> {
                //canWork =
                        //(newState == ConnectionState.CONNECTED || newState == ConnectionState
                // .RECONNECTED);
                log.info("ZookeeperManager stateChanged: {}, canWork:{}", newState);
                if (newState == ConnectionState.LOST
                        || newState == ConnectionState.SUSPENDED) {
                     counter.destroy();
                }
            });

            try {
                lock.acquire();
                counter.start();
            } catch (Exception e) {
                log.error("lock fail");
            } finally {
                while (!counter.isTerminated()) {
                    try {
                        Thread.sleep(3*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("counter terminated.");
                boolean release = false;
                while (!release) {
                    try {
                        lock.release();
                        log.info("release lock success.");
                        release = true;
                    } catch (Exception e) {
                        log.error("release fail");
                        try {
                            Thread.sleep(3 * 1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                client.close();
            }

            return true;
        }
    }
}

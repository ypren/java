/**
 *******************************************************************************
 *                         RoadDB Confidential
 *                    Copyright (c) RoadDB 2019
 *
 *      This software is furnished under license and may be used or
 *      copied only in accordance with the terms of such license.
 *******************************************************************************
 *
 * @file UpdateFileStatusSchedule.java
 * @brief UpdateFileStatusSchedule
 * ******************************************************************************
 */

package com.ypren.demo.zookeeper.distributedlock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

@Slf4j
public class Counter {
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private Long current;

    public Counter() {
        current = 1L;
    }

    public void start() {
        scheduledExecutor.scheduleWithFixedDelay(this::count,
                0,
                1,
                TimeUnit.SECONDS);
    }

    private void count() {
        log.info("{}", current);
        current++;
    }

    @PreDestroy
    public void destroy() {
        log.info("counter shutdown.");
        scheduledExecutor.shutdown();
    }

    public boolean isTerminated() {
        return scheduledExecutor.isShutdown()
                && scheduledExecutor.isTerminated();
    }

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new ExponentialBackoffRetry(3*1000, 10));
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client, "/test");
        try {
            lock.acquire();
            Thread.sleep(3*1000);
            log.info("lock success");
            lock.release();
            log.info("release success");

            lock.release();
            log.info("re-release success");
        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
        }
    }
}

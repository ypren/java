package com.ypren.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CompletableFutureTest {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        logger.info("main thread: {}", Thread.currentThread().getId());
        CompletableFuture future = computeInteger();
        Thread.sleep(3*1000);
        logger.info("hello");
        logger.info(future.get().toString());
    }

    public static CompletableFuture<Integer> computeInteger() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("compute thread: {}", Thread.currentThread().getId());
            int random = new Random().nextInt();
            return random;
        });
    }
}

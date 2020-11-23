package com.ypren.concurrent.lock;

import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CustomSpinLock {

    private static final Logger logger = LoggerFactory.getLogger(CustomSpinLock.class);

    private AtomicBoolean spinLock = new AtomicBoolean(true);

    public void lock() {
        boolean flag;
        do {
            flag = this.spinLock.compareAndSet(true, false);
        } while (!flag);
    }

    public void unlock() {
        this.spinLock.compareAndSet(false, true);
    }

    public static void main(String[] args) throws InterruptedException {
        CustomSpinLock spinLock = new CustomSpinLock();

        spinLock.lock();
        logger.info("main thread get lock success");
        Thread t1 = new Thread(() -> {

            logger.info("chile thread try to get lock.");
            spinLock.lock();
            logger.info("chile thread get lock success");
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("chile thread sleep 3s end, release lock");
        });
        t1.start();

        try {
            Thread.sleep(6*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("main thread sleep 6s end, release lock");
        spinLock.unlock();
    }
}

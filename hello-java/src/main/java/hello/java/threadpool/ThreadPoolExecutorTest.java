package hello.java.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("1");
        }, 1, 1, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("2");
        }, 2, 5, TimeUnit.SECONDS);
    }
}

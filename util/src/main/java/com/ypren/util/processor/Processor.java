package com.ypren.util.processor;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import com.ypren.util.runtimeutil.RuntimeUtil;
import com.ypren.util.timeutil.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Processor {
    public void process() {

        // test get local time
        String time = TimeUtil.getLocalTime();
        String date = TimeUtil.getLocalDate();
        log.info("local time: {}", time);
        log.info("local date: {}", date);

        // test RuntimeUtil
        int processor = RuntimeUtil.getProcessor();
        log.info("processor count: {}", processor);
    }

    public static boolean isItTimeToDo(final String when) {
        String[] whiles = when.split(";");
        if (whiles.length > 0) {
            Calendar now = Calendar.getInstance();
            for (String w : whiles) {
                int nowHour = Integer.parseInt(w);
                if (nowHour == now.get(Calendar.HOUR_OF_DAY)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isTimeToDelete() {
        String when = "04";
        if (isItTimeToDo(when)) {
            log.info("it's time to reclaim disk space, " + when);
            return true;
        }

        return false;
    }

    private static final double diskSpaceWarningLevelRatio =
            Double.parseDouble(System.getProperty("rocketmq.broker.diskSpaceWarningLevelRatio", "0.90"));

    private static String storePathCommitLog = System.getProperty("user.home") + File.separator +
            "store"
            + File.separator + "commitlog";

    public static double getDiskPartitionSpaceUsedPercent(final String path) {
        if (null == path || path.isEmpty())
            return -1;

        try {
            File file = new File(path);

            if (!file.exists())
                return -1;

            long totalSpace = file.getTotalSpace();
            log.info("totalSpace: {}", totalSpace);

            if (totalSpace > 0) {
                long freeSpace = file.getFreeSpace();
                long usedSpace = totalSpace - freeSpace;
                log.info("freeSpace: {}", freeSpace / (1024 * 1024 * 1024));
                log.info("usedSpace: {}", usedSpace / (1024 * 1024 * 1024));

                return usedSpace / (double) totalSpace;
            }
        } catch (Exception e) {
            return -1;
        }

        return -1;
    }

    public static long rollNextFile(final long offset) {
        int mappedFileSize = 1024 * 1024 * 1024;
        return offset + mappedFileSize - offset % mappedFileSize;
    }

    private static AtomicLong refCount = new AtomicLong(0);

    public static void main(String[] args) {
        boolean timeup = isTimeToDelete();
        log.info("timeup: {}", timeup);
        log.info("diskSpaceWarningLevelRatio: {}", diskSpaceWarningLevelRatio);
        log.info("getDiskPartitionSpaceUsedPercent: {}", getDiskPartitionSpaceUsedPercent(storePathCommitLog));
        log.info("rollNextFile: {}", rollNextFile(1073741824L));
        if (refCount.getAndIncrement() > 0) {
            log.info("ref count: {}", refCount.get());
        } else {
            log.info("ref count: {}", refCount.get());
        }
    }
}

package com.ypren.util.processor;

import com.ypren.util.timeutil.TimeUtil;
import com.ypren.util.runtimeutil.RuntimeUtil;

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
}

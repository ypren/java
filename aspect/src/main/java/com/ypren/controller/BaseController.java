package com.ypren.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    private final AtomicInteger counter;

    public BaseController() {
        this.counter = new AtomicInteger(0);
    }

    @GetMapping(path = "/auth")
    public void auth() {
        int count = counter.addAndGet(1);
        logger.info("count: {}", count);
        if (0 == count % 2) {
            throw new RuntimeException("auth fail");
        }
        return;
    }
}

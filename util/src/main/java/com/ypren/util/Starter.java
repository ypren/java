package com.ypren.util;

import com.ypren.util.processor.Processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Starter {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Starter.class, args);
        ctx.getBean(Processor.class).process();
        System.exit(0);
    }
}

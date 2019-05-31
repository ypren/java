package com.ypren.concurrent;

import com.ypren.concurrent.collection.ConCollections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class);
        ConCollections conCollections = ctx.getBean(ConCollections.class);
        conCollections.testConcurrentHashMap();
        conCollections.testSyncList();
    }
}

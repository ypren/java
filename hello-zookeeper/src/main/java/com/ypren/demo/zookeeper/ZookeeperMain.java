package com.ypren.demo.zookeeper;

import com.ypren.demo.zookeeper.distributedlock.CuratorDistributedLock;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class ZookeeperMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ZookeeperMain.class, args);
        applicationContext.getBean(CuratorDistributedLock.class).test();
    }
}

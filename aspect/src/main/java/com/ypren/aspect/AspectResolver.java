package com.ypren.aspect;

import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectResolver {

    private static final Logger logger = LoggerFactory.getLogger(AspectResolver.class);
    private final AtomicLong authTotal;
    private final AtomicLong authPass;
    private final AtomicLong authReject;

    public AspectResolver() {
        this.authTotal = new AtomicLong(0);
        this.authPass = new AtomicLong(0);
        this.authReject = new AtomicLong(0);
    }


    @Pointcut("execution(** com.ypren.controller.BaseController.auth(..))")
    public void auth() {
    }

    @Before("auth()")
    public void before() {
        long total = authTotal.addAndGet(1);
        logger.info("auth called, total {}", total);
    }

    @AfterReturning("auth()")
    public void afterReturning() {
        long pass = authPass.addAndGet(1);
        logger.info("pass {}", pass);
    }

    @AfterThrowing("auth()")
    public void afterThrowing() {
        long reject = authReject.addAndGet(1);
        logger.info("reject {}", reject);
    }
}

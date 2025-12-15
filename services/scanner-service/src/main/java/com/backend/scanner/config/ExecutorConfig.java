package com.backend.scanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    @Value("${scanner.threads.core:4}")
    private int corePoolSize;

    @Value("${scanner.threads.max:8}")
    private int maxPoolSize;

    @Value("${scanner.queue.capacity:200}")
    private int queueCapacity;

    @Bean(name = "scannerExecutor")
    public Executor scannerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("scanner-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}


package org.example.authentication_service.config.executor;

import lombok.Data;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Configuration
@ConfigurationProperties(prefix = "thread")
public class ExecutorConfig {

    private int threadCount;

    @Bean
    ExecutorService userDataProcessingService() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("UserDataProcessingThread-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newFixedThreadPool(threadCount, factory);
    }
}
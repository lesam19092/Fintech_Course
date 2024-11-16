package org.example.authentication_service.config.executor;

import lombok.Data;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@Configuration
@ConfigurationProperties(prefix = "thread")
@EnableAsync
public class ExecutorConfig {

    private int threadCountForUser;

    private int threadCountForEmail;

    @Bean
    ExecutorService userDataProcessingService() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("UserDataProcessingThread-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newFixedThreadPool(threadCountForUser, factory);
    }

    @Bean
    ExecutorService forSendingEmail() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("forSendingEmail-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newFixedThreadPool(threadCountForEmail, factory);


    }


}
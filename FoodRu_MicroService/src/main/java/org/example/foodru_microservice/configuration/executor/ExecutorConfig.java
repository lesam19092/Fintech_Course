package org.example.foodru_microservice.configuration.executor;

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
    ExecutorService asyncS3EmailDataExporter() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThreadForFetchingData-%d").priority(Thread.MAX_PRIORITY).build();
        return Executors.newFixedThreadPool(threadCount, factory);
    }
}
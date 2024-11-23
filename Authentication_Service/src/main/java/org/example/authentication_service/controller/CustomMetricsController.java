package org.example.authentication_service.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class CustomMetricsController {

    private final Counter customCounter;

    public CustomMetricsController(MeterRegistry registry) {
        this.customCounter = registry.counter("custom_requests_total", "type", "custom_metric");
    }

    @GetMapping("/custom")
    public void incrementCustomMetric() {
        var requestId = UUID.randomUUID().toString();

        try (var ignored = MDC.putCloseable("requestId", requestId + " я тут")) {
            log.info("with structed log");
        }

        customCounter.increment();
    }
}
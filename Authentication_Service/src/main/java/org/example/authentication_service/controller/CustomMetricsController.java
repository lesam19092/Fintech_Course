package org.example.authentication_service.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomMetricsController {

    private final Counter customCounter;

    public CustomMetricsController(MeterRegistry registry) {
        this.customCounter = registry.counter("custom_requests_total", "type", "custom_metric");
    }

    @GetMapping("/custom")
    public void incrementCustomMetric()
    {
        customCounter.increment();
    }
}
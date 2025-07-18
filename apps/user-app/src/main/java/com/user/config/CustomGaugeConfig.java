package com.user.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomGaugeConfig {

    private final AtomicInteger gaugeValue = new AtomicInteger(0);

    public CustomGaugeConfig(MeterRegistry meterRegistry, ThreadPoolTaskExecutor executor) {
        // Register the gauge
        Gauge.builder("getQueueCapacity", executor::getQueueCapacity)
                .description("A custom gauge metric example")
                .tag("type", "example")
                .register(meterRegistry);
        // Register the gauge
        Gauge.builder("getQueueSize", executor::getQueueSize)
                .description("A custom gauge metric example")
                .tag("type", "example")
                .register(meterRegistry);
    }

    // Example method to manipulate the gauge value
    public void increment() {
        gaugeValue.incrementAndGet();
    }

    public void decrement() {
        gaugeValue.decrementAndGet();
    }

    public int getValue() {
        return gaugeValue.get();
    }
}
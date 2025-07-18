package com.user.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadPoolMetricsConfig implements AsyncConfigurer {

    private final MeterRegistry meterRegistry;

    public ThreadPoolMetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("async-thread-");
        executor.initialize();

        // Register thread pool metrics
        meterRegistry.gauge("lalala", 1.0);
        meterRegistry.gauge("lalala2", 1);

        // Update the value dynamically
        meterRegistry.gauge("threadpool.active.threads", executor, ThreadPoolTaskExecutor::getActiveCount);
        meterRegistry.gauge("threadpool.queue.size", executor.getThreadPoolExecutor(), executorService -> executorService.getQueue().size());
        meterRegistry.gauge("threadpool.queue.remaining", executor.getThreadPoolExecutor().getQueue(), BlockingQueue::remainingCapacity);
        meterRegistry.gauge("threadpool.pool.size", executor, ThreadPoolTaskExecutor::getPoolSize);
        meterRegistry.gauge("threadpool.core.pool.size", executor, ThreadPoolTaskExecutor::getCorePoolSize);
        meterRegistry.gauge("threadpool.max.pool.size", executor, ThreadPoolTaskExecutor::getMaxPoolSize);
        meterRegistry.gauge("threadpool.completed.tasks", executor.getThreadPoolExecutor(), ThreadPoolExecutor::getCompletedTaskCount);

        return executor;
    }

}
package com.user.config;

import feign.RequestInterceptor;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
  @Bean
  public RequestInterceptor tracingInterceptor(Tracer tracer) {
    return template -> {
      if (tracer.currentSpan() != null) {
        TraceContext context = tracer.currentSpan().context();
        template.header("X-B3-TraceId", context.traceId());
        template.header("X-B3-SpanId", context.spanId());
      }
    };
  }
}
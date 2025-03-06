package com.harmelodic.logbackappmetrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LogbackAppMetricsApplication {
    public static final Logger logger = LoggerFactory.getLogger(LogbackAppMetricsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LogbackAppMetricsApplication.class, args);
    }

    @EventListener
    public void onReady(ApplicationReadyEvent applicationReadyEvent) {
        for (int i = 0; i < 10; i++) {
            // This bumps `logback_events_total{level="warn"}` each time.
            logger.warn("Normal Warning log: {}", i);
        }

        for (int i = 0; i < 10; i++) {
            // I expect this to bump `logback_events_total{level="error"}` each time, but it doesn't.
            logger.atError()
                    .addKeyValue("someKey", "someValue")
                    .log("AtWarn Warning Log: {}", i);
        }
    }
}

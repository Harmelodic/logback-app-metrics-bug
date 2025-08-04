package com.harmelodic.bugs.metrics.app.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {
    public static final Logger logger = LoggerFactory.getLogger(LogProducer.class);

    /// Produces a bunch of logs using the regular SLF4J API.
    /// Spring uses Logback for logging, I expect this to bump `logback_events_total{level="warn"}` each time,
    /// and it does!
    public void produceRegularLogs(int numberOfLogs) {
        for (int i = 0; i < numberOfLogs; i++) {
            // This bumps `logback_events_total{level="warn"}` each time.
            logger.warn("Normal Warning log: {}", i);
        }
    }

    /// Produces a bunch of logs using the SLF4J Fluent API.
    /// Spring uses Logback for logging, I expect this to bump `logback_events_total{level="warn"}` each time,
    /// but it doesn't.
    public void produceFluentLogs(int numberOfLogs) {
        for (int i = 0; i < numberOfLogs; i++) {
            logger.atWarn()
                    .addKeyValue("someKey", "someValue")
                    .log("AtWarn Warning Log: {}", i);
        }
    }
}

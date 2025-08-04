package com.harmelodic.bugs.metrics.app.logback;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LogProducerTest {

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private LogProducer logProducer;

    @Test
    @DirtiesContext
    void produceRegularLogs() {
        logProducer.produceRegularLogs(10);
        assertEquals(10, meterRegistry.counter("logback.events", Tags.of("level", "warn")).count());
    }

    /// Ideally, this test should fail.
    /// Ideally, the metric would be 10.0
    ///
    /// I hope this test fails at some point.
    @Test
    @DirtiesContext
    void produceFluentLogs() {
        logProducer.produceFluentLogs(10);
        assertEquals(0, meterRegistry.counter("logback.events", Tags.of("level", "warn")).count());
    }
}

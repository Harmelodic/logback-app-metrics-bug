package com.harmelodic.bug.metrics.app.logback;

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

    /// This test passes! Bug fixed since 3.5.8 / 4.0.0!
    @Test
    @DirtiesContext
    void produceFluentLogs() {
        logProducer.produceFluentLogs(10);
        assertEquals(10, meterRegistry.counter("logback.events", Tags.of("level", "warn")).count());
    }
}

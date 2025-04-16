# logback-app-metrics-bug

An example Spring Boot MVC Web Application to showcase an issue with Logback and metrics.

Boot app with: `mvn clean spring-boot:run`.

This app boots and then produces 20 logs once the application is ready: 10 warning logs and 10 error logs.

Prometheus metrics:

- http://localhost:8080/actuator/prometheus
- Search for `logback_events`
- Notice that for tag `warn` the metric is 10.0, correct.
- Notice that for tag `error` the metric is 0.0, incorrect.

Spring metrics:

- http://localhost:8080/actuator/metrics/logback.events?tag=level:warn
- Notice that the metric is 10.0, correct.
- http://localhost:8080/actuator/metrics/logback.events?tag=level:error
- Notice that the metric is 0.0, incorrect.

The metrics don't seem to be recorded when _**fluent-logging**_ is used.

Where's the bug?

- FYI: Logback doesn't actually produce any metrics of any kind.
- Spring uses Micrometer to handle the metrics instrumentation.
- Micrometer does Logback metrics instrumentation [here](https://github.com/micrometer-metrics/micrometer/blob/main/micrometer-core/src/main/java/io/micrometer/core/instrument/binder/logging/LogbackMetrics.java).
  - Here is where the `logback.events` counters are actually defined.
  - Micrometer implements a Logback [TurboFilter](https://logback.qos.ch/manual/filters.html#TurboFilter) to capture logs and increment counters.
  - For performance reasons, they sometimes don't increment counters if they determine they don't need to.
  - I suspect it is with this performance logic where the problem lies.
  - There seems to be [a GitHub issue already created for this problem](https://github.com/micrometer-metrics/micrometer/issues/4404).

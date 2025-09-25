# logback-app-metrics-bug

An example Spring Boot MVC Web Application to showcase an issue with Logback and metrics.

Check the tests, to see how the `logback.events` metric bug is manifested.

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

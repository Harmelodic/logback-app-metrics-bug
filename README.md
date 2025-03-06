# logback-app-metrics

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

The metrics don't seem to be recorded when fluent-logging is used.

Where's the bug?... Don't know yet!

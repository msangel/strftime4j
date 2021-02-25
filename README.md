# strftime4j
strftime time formatter for java

[![Coverage Status](https://coveralls.io/repos/github/msangel/strftime4j/badge.svg?branch=master)](https://coveralls.io/github/msangel/strftime4j?branch=master)

Supported types: `java.util.Date`, `java.util.Calendar` and all date-time formats of java8, like:
* `java.time.Instant`
* `java.time.OffsetDateTime`
* `java.time.ZonedDateTime`
* `java.time.LocalDateTime`
* `java.time.ChronoLocalDateTime`
* `java.time.Year`
* any other `TemporalAccessor`

Thread-save.

Support all the standard syntax of `strftime` function in C.
The Ruby's implementation was taken as an example spec.

# strftime4j
strftime time formatter for java

Support all the standard syntax of `strftime` function in C.

The Ruby's implementation was taken as an example spec(it also follows C conventions).

[![Build Status](https://travis-ci.com/msangel/strftime4j.svg?branch=master)](https://travis-ci.com/msangel/strftime4j)
[![Coverage Status](https://coveralls.io/repos/github/msangel/strftime4j/badge.svg?branch=master)](https://coveralls.io/github/msangel/strftime4j?branch=master)

## Usage example:
```java
// strict mode allow to ensure that the string will be formatted as expected:
ZonedDateTime t = ZonedDateTime.parse("2021-02-16T23:43:00-02:00");
String res = StrftimeFormatter.ofStrictPattern("Printed on %m/%d/%Y").format(t);
assertEquals("Printed on 02/16/2021", res);

// strict mode will throw an exception if input object does 
// not contains required temporal field
try {
    YearMonth ym = YearMonth.of(2020, 1);
    StrftimeFormatter.ofStrictPattern("Printed on %m/%d/%Y").format(ym);
} catch (Exception e) {
    // exception caught, 
    // as the input object YearMonth does not 
    // contains information about the Date in it!
}

// safe mode will ignore missing fields
YearMonth ym = YearMonth.of(2020, 1);
res = StrftimeFormatter.ofSafePattern("Printed on %m/%d/%Y").format(ym);
assertEquals("Printed on 01//2020", res);
```
Note:
Expression parsing behaves the same way in both strict and safe mode according to strftime function expectations. The difference between them is only in the way they handle missing date/time properties from the input date/time object during rendering time.


## Supported types
* `java.util.Date` and its successors
* `java.util.Calendar`
* Java8 time API types, like:
    * `java.time.Instant`
    * `java.time.OffsetDateTime`
    * `java.time.ZonedDateTime`
    * `java.time.LocalDateTime`
    * `java.time.ChronoLocalDateTime`
    * `java.time.Year`
    * any other `TemporalAccessor` successors


## Thread-safety
This library is thread-safe. 
After creation the `StrftimeFormatter` object is immutable and can be used in a multithreaded environment. 

## Possible future optimisations:
This library is ready for use.
But there are no limits for perfection.
Here's a list of some improvements, I'd like to implement here at some future:

* Use `FromTemporalFieldFormat` instead of `PatternFormat` where possible, as in the end they both use the same low-level API, but in the first case - fewer objects will be created(also less memory and less stacktrace to the same important exception in the `strict` mode).
* Instead of `PatternFormat` create a new kind of `HybridFormat` that will work with conversion combinations and will delegate its parts rendering to known conversions.
* Expose method for short timezone names aliases mapping, so end-user be able to override short timezone names with own mapping.
* Keep codepoints list in `TextToken` as sublist from the input(it is immutable anyway). Less memory will be used.

## License
Standard MIT license.

In short:
* You may use the work commercially
* You may make changes to the work
* You may distribute the compiled code and/or source
* You may incorporate the work into something that has a more restrictive license.
* You may use the work for private use.
* The work is provided "as is". You may not hold the author liable.

In long [read here](https://github.com/msangel/strftime4j/blob/master/LICENSE)

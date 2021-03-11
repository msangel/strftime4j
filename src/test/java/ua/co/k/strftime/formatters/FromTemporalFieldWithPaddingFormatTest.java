package ua.co.k.strftime.formatters;

import org.junit.Ignore;
import org.junit.Test;
import ua.co.k.strftime.StrftimeFormatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * %k
 */
public class FromTemporalFieldWithPaddingFormatTest {

    @Test
    public void testStrictPattern() {

        // HourOfDay exists only in local time as in perspective of instance it has so many definitions as there exists timezones

        try {
            StrftimeFormatter.ofStrictPattern("hour of day: %k").format(Instant.parse("1970-01-01T19:00:14Z"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: HourOfDay"));
        }

        try {
            StrftimeFormatter.ofStrictPattern("%k").format(Month.JANUARY);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: HourOfDay"));
        }
        LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 3, 13, 36);
        String expected = localDateTime.format(DateTimeFormatter.ofPattern("H"));
        assertEquals(expected, StrftimeFormatter.ofStrictPattern("%k").format(localDateTime));

        assertEquals(" 5", StrftimeFormatter.ofStrictPattern("%k").format(LocalDateTime.of(2020, 2, 3, 5, 36)));

    }

    @Test
    public void testSafePattern() {
        Instant t = Instant.parse("1970-01-01T18:00:14Z");

        String res = StrftimeFormatter.ofSafePattern("hour of day: %k").format(t);
        assertEquals("hour of day: ", res);

        res = StrftimeFormatter.ofSafePattern(">%k<").format(Month.JANUARY);
        assertEquals("><", res);

        LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 3, 13, 36);
        assertEquals("13", StrftimeFormatter.ofSafePattern("%k").format(localDateTime));

        assertEquals(" 3", StrftimeFormatter.ofSafePattern("%k").format(LocalDateTime.of(2020, 2, 3, 3, 36)));
    }

}

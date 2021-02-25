package ua.co.k.strftime.formatters;

import org.junit.Ignore;
import org.junit.Test;
import ua.co.k.strftime.StrftimeFormatter;

import java.time.Instant;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * %k
 */
public class FromTemporalFieldWithPaddingFormatTest {

    @Test
    @Ignore
    public void testStrictPattern() {
        Instant t = Instant.parse("1970-01-01T19:00:14Z");

        String res = StrftimeFormatter.ofStrictPattern("hour of day: %k").format(t);
        assertEquals("hour of day: 19", res);

        try {
            StrftimeFormatter.ofStrictPattern("%k").format(Month.JANUARY);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: HourOfDay"));
        }
    }

    @Test
    @Ignore
    public void testSafePattern() {
        Instant t = Instant.parse("1970-01-01T18:00:14Z");

        String res = StrftimeFormatter.ofSafePattern("hour of day: %k").format(t);
        assertEquals("hour of day: 18", res);

        res = StrftimeFormatter.ofSafePattern(">%k<").format(Month.JANUARY);
        assertEquals("><", res);
    }

}

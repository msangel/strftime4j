package ua.co.k.strftime.formatters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.time.YearMonth;

import org.junit.Test;

import ua.co.k.strftime.StrftimeFormatter;

/**
 * %N
 */
public class FractionalSecondsFormatTest {
    @Test
    public void testSafe() {
        YearMonth ym = YearMonth.of(2000, 1);
        String res = StrftimeFormatter.ofSafePattern("this %N").format(ym);
        assertEquals("this ", res);

        StrftimeFormatter.ofSafePattern("this %N").format(Instant.ofEpochMilli(555L));
        assertEquals("this ", res);
    }

    @Test
    public void testStrict() {
        YearMonth ym = YearMonth.of(2000, 1);
        try {
            StrftimeFormatter.ofStrictPattern("this %N").format(ym);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: MilliOfSecond"));
        }


        String res = StrftimeFormatter.ofStrictPattern("this %N").format(Instant.ofEpochMilli(555L));
        assertEquals("this 555000000", res);
    }
}

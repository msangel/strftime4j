package ua.co.k.strftime.formatters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.co.k.strftime.StrftimeFormatter.ofSafePattern;
import static ua.co.k.strftime.StrftimeFormatter.ofStrictPattern;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

import org.junit.Test;

public class ZoneOffsetFormatTest {
    @Test
    public void testZoneOffsetFormat() {
        Temporal time = ZonedDateTime.parse("2007-12-03T10:15:30+01:30:25");
        String res = ofStrictPattern("%z").format(time);
        assertEquals("+0130", res);

        res = ofStrictPattern("%:z").format(time);
        assertEquals("+01:30", res);

        res = ofStrictPattern("%::z").format(time);
        assertEquals("+01:30:25", res);

        res = ofStrictPattern("%:::z").format(time);
        assertEquals("%:::z", res);

        time = LocalDateTime.now();
        res = ofSafePattern(">%z<").format(time);
        assertEquals("><", res);

        res = ofSafePattern(">%:z<").format(time);
        assertEquals("><", res);

        res = ofSafePattern(">%::z<").format(time);
        assertEquals("><", res);

        res = ofSafePattern(">%:::z<").format(time);
        assertEquals(">%:::z<", res);

        try {
            ofStrictPattern("%z").format(time);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: OffsetSeconds"));
        }

        try {
            ofStrictPattern("%::z").format(time);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: OffsetSeconds"));
        }

        res = ofStrictPattern("%:::z").format(time);
        assertEquals("%:::z", res);
    }
}

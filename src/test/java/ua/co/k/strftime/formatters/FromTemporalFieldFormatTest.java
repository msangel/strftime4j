package ua.co.k.strftime.formatters;

import org.junit.Test;
import ua.co.k.strftime.StrftimeFormatter;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class FromTemporalFieldFormatTest {
    @Test
    public void testCase() {
        LocalTime t = LocalTime.MIDNIGHT;
        // %u - Day of the week as a decimal, Monday being 1. (1..7)
        String res = StrftimeFormatter.ofSafePattern("lol%uok").format(t);
        assertEquals("lolok", res);

        try {
            StrftimeFormatter.ofStrictPattern("lol%uok").format(t);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: DayOfWeek"));
        }
    }
}

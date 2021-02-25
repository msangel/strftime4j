package ua.co.k.strftime.formatters;

import org.junit.Test;
import ua.co.k.strftime.StrftimeFormatter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class DayOfWeekFormatTest {
    @Test
    public void testMath() {
        assertEquals(2, fun(1, 2));
        assertEquals(3, fun(7, 2));
        assertEquals(7, fun(3, 2));
        assertEquals(5, fun(2, 6));
    }

    private int fun(int startOfWeekDayNumber, int lookupDayInOldSystem) {
        return ((7 + lookupDayInOldSystem - startOfWeekDayNumber) % 7) + 1;
    }

    @Test
    public void testCaseSafePattern() {
        LocalTime l = LocalTime.MIDNIGHT;
        String res = StrftimeFormatter.ofSafePattern("ww%%%w%%").format(l);
        assertEquals("ww%%", res);

        res = StrftimeFormatter.ofSafePattern("ww%%%w%%")
                .format(LocalDateTime.parse("2021-02-25T10:15:30"));
        assertEquals("ww%4%", res);

        res = StrftimeFormatter.ofSafePattern("ww%%%04w%%")
                .format(LocalDateTime.parse("2021-02-21T10:15:30"));
        assertEquals("ww%0000%", res);
    }

    @Test
    public void testCaseStrictPattern() {
        LocalTime l = LocalTime.MIDNIGHT;
        try {
            StrftimeFormatter.ofStrictPattern("ww%%%w%%").format(l);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: DayOfWeek"));
        }

        String res = StrftimeFormatter.ofStrictPattern("ww%%%04w%%")
                .format(LocalDateTime.parse("2021-02-21T10:15:30"));
        assertEquals("ww%0000%", res);
    }
}

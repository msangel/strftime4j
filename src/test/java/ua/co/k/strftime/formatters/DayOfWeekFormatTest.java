package ua.co.k.strftime.formatters;

import org.junit.Test;

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
}

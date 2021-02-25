package ua.co.k.strftime.formatters;

import org.junit.Test;
import ua.co.k.strftime.StrftimeFormatter;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * %C
 */
public class CenturyDateFormatTest {
    @Test
    public void testCase() {
        LocalTime t = LocalTime.MIDNIGHT;
        String res = StrftimeFormatter.ofSafePattern("lol %C yay").format(t);
        assertEquals("lol  yay", res);

        try {
            StrftimeFormatter.ofStrictPattern("lol %C yay").format(t);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unsupported field: YearOfEra"));
        }
    }
}

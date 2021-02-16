package ua.co.k.strftime;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static ua.co.k.strftime.StrftimeFormatter.ofStrictPattern;

public class StrftimeFormatterTest {
    @Test
    public void testParser() {
        ZonedDateTime t = ZonedDateTime.parse("2021-02-16T23:43:00-02:00");
        StrftimeFormatter f = StrftimeFormatter.ofStrictPattern("asdsdfsdf");
        String res = f.format(t);
        assertEquals("asdsdfsdf", res);

        res = StrftimeFormatter.ofStrictPattern("Printed on %m/%d/%Y").format(t);
        assertEquals("Printed on 02/16/2021", res);

        res = StrftimeFormatter.ofStrictPattern("").format(t);
        assertEquals("", res);

        res = StrftimeFormatter.ofStrictPattern("%").format(t);
        assertEquals("%", res);

        res = StrftimeFormatter.ofStrictPattern("%%").format(t);
        assertEquals("%", res);

        // %<flags><width><modifier><conversion>
        res = StrftimeFormatter.ofStrictPattern("%_50E%").format(t);
        assertEquals("%", res);

    }
}

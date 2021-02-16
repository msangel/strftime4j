package ua.co.k.strftime;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static ua.co.k.strftime.StrftimeFormatter.ofPattern;

public class StrftimeFormatterTest {
    @Test
    public void testParser() {
        ZonedDateTime t = ZonedDateTime.parse("2021-02-16T23:43:00-02:00");
        StrftimeFormatter f = ofPattern("asdsdfsdf");
        String res = f.format(t);
        assertEquals("asdsdfsdf", res);

        res = ofPattern("Printed on %m/%d/%Y").format(t);
        assertEquals("Printed on 02/16/2021", res);

        res = ofPattern("").format(t);
        assertEquals("", res);

        res = ofPattern("%").format(t);
        assertEquals("%", res);

        res = ofPattern("%%").format(t);
        assertEquals("%", res);

        // %<flags><width><modifier><conversion>
        res = ofPattern("%_50E%").format(t);
        assertEquals("%", res);

    }
}

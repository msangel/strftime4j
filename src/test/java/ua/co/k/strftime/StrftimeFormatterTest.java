package ua.co.k.strftime;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static ua.co.k.strftime.StrftimeFormatter.ofStrictPattern;

public class StrftimeFormatterTest {
    @Test
    public void testParserCommons() {
        ZonedDateTime t = ZonedDateTime.parse("2021-02-16T23:43:00-02:00");
        StrftimeFormatter f = ofStrictPattern("asdsdfsdf");
        String res = f.format(t);
        assertEquals("asdsdfsdf", res);

        res = ofStrictPattern("Printed on %m/%d/%Y").format(t);
        assertEquals("Printed on 02/16/2021", res);

        res = ofStrictPattern("").format(t);
        assertEquals("", res);

        res = ofStrictPattern("%").format(t);
        assertEquals("%", res);

        res = ofStrictPattern("%%").format(t);
        assertEquals("%", res);

        // %<flags><width><modifier><conversion>
        res = ofStrictPattern("%_50E%").format(t);
        assertEquals("%", res);

    }

    @Test
    public void testRules() {
        String[][] data = {
                {"%a", "Mon"},
                {"%0^a", "MON"},
                {"%-0--_0___-0-a", "Mon"},
                {"%-0--_0_^__-0-a", "MON"},
                {"%-0--_0_#__-0-a", "MON"},
                {"%:a", "%:a"},
                {"%Ea", "%Ea"},
                {"%Oa", "%Oa"},
                {"%_5a", "  Mon"},
                {"%0_0_05a", "00Mon"},
                {"%0_0_0-5a", "Mon"}
        };
        ZonedDateTime t = ZonedDateTime.parse("2007-01-01T01:01:01-02:00");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }
    }


}

package ua.co.k.strftime;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StrftimeFormatterTest {
    @Test
    public void testParser() {
        StrftimeFormatter f = StrftimeFormatter.ofPattern(null);
        List<Token> tokens = f.parse("asdsdfsdf");
        String res = f.printStream(tokens);
        assertEquals("asdsdfsdf", res);

        tokens = f.parse("Printed on %m/%d/%Y");
        res = f.printStream(tokens);
        assertEquals("Printed on <m>/<d>/<Y>", res);

        tokens = f.parse("");
        res = f.printStream(tokens);
        assertEquals("", res);

        tokens = f.parse("%");
        res = f.printStream(tokens);
        assertEquals("%", res);

        tokens = f.parse("%%");
        res = f.printStream(tokens);
        assertEquals("<%>", res);

        // %<flags><width><modifier><conversion>
        tokens = f.parse("%_50E%");
        res = f.printStream(tokens);
        assertEquals("<%>", res);

    }
}

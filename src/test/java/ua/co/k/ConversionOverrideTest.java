package ua.co.k;

import org.junit.Test;
import ua.co.k.strftime.LiteralPattern;
import ua.co.k.strftime.StrftimeFormatter;
import ua.co.k.strftime.formatters.DateTimeFormatterFactory;

import static org.junit.Assert.assertEquals;

public class ConversionOverrideTest {

    @Test
    public void testOverride() {
        DateTimeFormatterFactory.override('~', new LiteralPattern("tilda"));
        String res = StrftimeFormatter.ofStrictPattern("lol its a %~ here!").format(null);
        assertEquals("lol its a tilda here!", res);
    }
}

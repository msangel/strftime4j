package ua.co.k.strftime;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static ua.co.k.strftime.StrftimeFormatter.ofStrictPattern;

public class StrftimeFormatterTest {
    @Test
    public void testCodeFromReadMe() {
        // strict mode allow to ensure that the string will be formatted as expected:
        ZonedDateTime t = ZonedDateTime.parse("2021-02-16T23:43:00-02:00");
        String res = StrftimeFormatter.ofStrictPattern("Printed on %m/%d/%Y").format(t);
        assertEquals("Printed on 02/16/2021", res);

        // strict mode will throw an exception if input object does
        // not contains required temporal field
        try {
            YearMonth ym = YearMonth.of(2020, 1);
            StrftimeFormatter.ofStrictPattern("Printed on %m/%d/%Y").format(ym);
        } catch (Exception e) {
            // exception caught,
            // as the input object YearMonth does not
            // contains information about the Date in it!
        }

        // safe mode will ignore missing fields
        YearMonth ym = YearMonth.of(2020, 1);
        res = StrftimeFormatter.ofSafePattern("Printed on %m/%d/%Y").format(ym);
        assertEquals("Printed on 01//2020", res);
    }
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

        // ruby says:
        // `strftime': invalid format: %_50E% (ArgumentError)
        // %<flags><width><modifier><conversion>
        res = ofStrictPattern("%_50E%").format(t);
        assertEquals("%_50E%", res);
        res = ofStrictPattern("%_%").format(t);
        assertEquals("%_%", res);
        res = ofStrictPattern("%_E%").format(t);
        assertEquals("%_E%", res);

        // out of char range conversion
        res = ofStrictPattern("%\uD83C\uDF39").format(t); // %🌹
        assertEquals("%\uD83C\uDF39", res);

        res = ofStrictPattern("%0\n").format(t);
        assertEquals("%0\n", res);

    }

    @Test
    public void testRules() {
        String[][] data = {
                {"%a", "Tue"},
                {"%0^a", "TUE"},
                {"%-0--_0___-0-a", "Tue"},
                {"%-0--_0_^__-0-a", "TUE"},
                {"%-0--_0_#__-0-a", "TUE"},
                {"%:a", "%:a"},
                {"%Ea", "%Ea"},
                {"%Oa", "%Oa"},
                {"%_5a", "  Tue"},
                {"%0_0_05a", "00Tue"},
                {"%0_0_0-5a", "Tue"},

                {"%A", "Tuesday"},
                {"%0^A", "TUESDAY"},
                {"%-0--_0___-0-A", "Tuesday"},
                {"%-0--_0_^__-0-A", "TUESDAY"},
                {"%-0--_0_#__-0-A", "TUESDAY"},
                {"%:A", "%:A"},
                {"%EA", "%EA"},
                {"%OA", "%OA"},
                {"%_10A", "   Tuesday"},
                {"%0_0_010A", "000Tuesday"},
                {"%0_0_0-5A", "Tuesday"},

                {"%b", "Jan"},
                {"%0^b", "JAN"},
                {"%-0--_0___-0-b", "Jan"},
                {"%-0--_0_^__-0-b", "JAN"},
                {"%-0--_0_#__-0-b", "JAN"},
                {"%:b", "%:b"},
                {"%Eb", "%Eb"},
                {"%Ob", "%Ob"},
                {"%_5b", "  Jan"},
                {"%0_0_05b", "00Jan"},
                {"%0_0_0-5b", "Jan"},

                {"%B", "January"},
                {"%0^B", "JANUARY"},
                {"%-0--_0___-0-B", "January"},
                {"%-0--_0_^__-0-B", "JANUARY"},
                {"%-0--_0_#__-0-B", "JANUARY"},
                {"%:B", "%:B"},
                {"%EB", "%EB"},
                {"%OB", "%OB"},
                {"%_10B", "   January"},
                {"%0_0_010B", "000January"},
                {"%0_0_0-10B", "January"},
                {"%C", "21"},
                {"%_4C", "  21"},
                {"%d", "02"},
                {"%_d", " 2"},
                {"%D", "01/02/07"},
                {"%e", "2"},
                {"%05e", "00002"},
                {"%f", "%f"},
                {"%F", "2007-01-02"},
                {"%g", "07"},
                {"%-g", "7"},
                {"%_g", " 7"},
                {"%G", "2007"},
                {"%-G", "2007"},
                {"%_G", "2007"},
                {"%h", "Jan"},
                {"%^h", "JAN"},
                {"%H", "13"},
                {"%i", "%i"},
                {"%I", "01"},
                {"%j", "002"},
                {"%J", "%J"},
                {"%k", "13"},
                {"%K", "%K"},
                {"%l", " 1"},
                {"%L", "678"},
                {"%m", "01"},
                {"%-m", "1"},
                {"%_m", " 1"},
                {"%M", "04"},
                {"%-M", "4"},
                {"%03M", "004"},
                {"%n", "\n"},
                {"%_n", "%_n"},
                {"%-n", "%-n"},
                {"%3N", "678"},
                {"%6N", "678000"},
                {"%9N", "678000000"},
                {"%N", "678000000"},
                {"%12N", "678000000000"},
                {"%o", "%o"},
                {"%O", "%O"},
                {"%p", "PM"},
                {"%P", "pm"},
                {"%r", "01:04:05 PM"},
                {"%R", "13:04"},
                {"%s", "1167764645"},
                {"%S", "05"},
                {"%-S", "5"},
                {"%_S", " 5"},
                {"%t", "\t"},
                {"%T", "13:04:05"},
                {"%u", "2"},
                {"%U", "00"},
                {"%_U", " 0"},
                {"%v", "2-JAN-2007"},
                {"%V", "01"},
                {"%w", "2"},
                {"%W", "01"},
                {"%x", "01/02/07"},
                {"%X", "13:04:05"},
                {"%y", "07"},
                {"%Y", "2007"},
                {"%z", "-0600"},
                {"%Z", "Central Standard Time"},
        };
        ZonedDateTime t = ZonedDateTime.parse("2007-01-02T13:04:05.678-06:00[America/Winnipeg]");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }

        data = new String[][]{
                {"%H", "01"},
                {"%k", " 1"},
                {"%l", " 1"},
                {"%L", "000"},
                {"%-L", "0"},
                {"%p", "AM"},
                {"%P", "am"},
                {"%r", "01:04:05 AM"},
                {"%R", "01:04"},
                {"%w", "0"},
                {"%z", "-0600"},
                {"%Z", "-06:00"},
        };

        // Sunday
        t = ZonedDateTime.parse("2007-01-07T01:04:05-06:00");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }

        data = new String[][]{
                // %w - Day of the week (Sunday is 0, 0..6)
                // Monday is 1
                {"%w", "1"},
        };
        // Monday
        t = ZonedDateTime.parse("2007-01-01T01:04:05-06:00");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }
        // Friday
        data = new String[][]{
                {"%w", "5"},
                {"%U", "08"},
                {"%W", "08"},
                {"%V", "08"},
        };
        t = ZonedDateTime.parse("2021-02-26T01:04:05-06:00");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }

        data = new String[][]{
                {"%U", "00"},
                {"%W", "00"},
                {"%V", "53"},
        };
        t = ZonedDateTime.parse("2021-01-02T01:04:05-06:00");
        for(String[] sample: data) {
            assertEquals("test for pattern: " + sample[0], sample[1], ofStrictPattern(sample[0]).format(t));
        }

    }

    @Test
    public void testCombinationPatterns() {
        // (%a %b %e %T %Y)
        String[][] data = {
                {"%c", "%a %b %e %T %Y", "Mon Jan 1 01:01:01 2007"},
                // ignore padding flags
                {"%0100c","%a %b %e %T %Y","Mon Jan 1 01:01:01 2007"},
                // respect case flags
                {"%^c","%^a %^b %^e %^T %^Y","MON JAN 1 01:01:01 2007"},

                {"%D", "%m/%d/%y", "01/01/07"},
                // ignore padding flags
                {"%0100D","%m/%d/%y","01/01/07"},
                // respect case flags
                {"%^D","%^m/%^d/%^y","01/01/07"},

                {"%F", "%Y-%m-%d", "2007-01-01"},
                {"%r","%I:%M:%S %p", "01:01:01 AM"},


                {"%T", "%H:%M:%S", "01:01:01"},
                // ignore padding flags
                {"%0100T","%H:%M:%S","01:01:01"},

                {"%v", "%e-%^b-%4Y", "1-JAN-2007"},
                // ignore padding flags
                {"%0100v","%e-%^b-%4Y","1-JAN-2007"},
        };

        ZonedDateTime t = ZonedDateTime.parse("2007-01-01T01:01:01-02:00");
        for(String[] sample: data) {

            String format = ofStrictPattern(sample[0]).format(t);
            assertEquals("test for pattern: " + sample[0], sample[2], format);
            assertEquals("pattern [" + sample[0] + "] should give same output as [" + sample[1] + "]", ofStrictPattern(sample[0]).format(t), ofStrictPattern(sample[1]).format(t));
        }
    }

    @Test
    public void testWithLocale() {
        ZonedDateTime val = ZonedDateTime.of(LocalDateTime.of(2021, 11, 3, 16, 40, 44), ZoneId.of("America/Los_Angeles"));
        String res = ofStrictPattern("%Y-%m-%d %H:%M:%S %Z")
                .withLocale(Locale.GERMANY)
                .format(val);

        int classVersion = Double.valueOf(System.getProperty("java.class.version")).intValue();
        // JDK localisation changed text starting 9th java version, its 53 java class version
        if (classVersion > 52) {
            assertEquals("2021-11-03 16:40:44 Nordamerikanische Westküsten-Sommerzeit", res);
        } else {
            assertEquals("2021-11-03 16:40:44 Pazifische Sommerzeit", res);
        }
    }

    @Test
    public void testWithCustomZoneIdResolver() {
        // works only for legacy Calendar types
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EET"));

        String res = ofStrictPattern("%Z").format(cal);
        assertEquals("Eastern European Time", res);

        res = ofStrictPattern("%Z").withZoneIdResolver(new ZoneIdResolver() {
            @Override
            public ZoneId toZoneId(String id) {
                if ("EET".equalsIgnoreCase(id)) {
                    return ZoneId.of("America/Dominica");
                }
                return ZoneId.of(id, ZoneId.SHORT_IDS);
            }
        }).format(cal);
        assertEquals("Atlantic Standard Time", res);
    }
}

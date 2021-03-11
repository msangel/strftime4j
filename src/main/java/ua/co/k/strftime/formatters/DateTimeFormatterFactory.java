package ua.co.k.strftime.formatters;

import ua.co.k.strftime.LiteralPattern;
import ua.co.k.strftime.formatters.FromTemporalFieldWithPaddingFormat.Padding;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

public class DateTimeFormatterFactory {

    final static Map<Character, HybridFormat> translate = new HashMap<>();
    static {
        translate.put('a', new PatternFormat("EEE", false) );
        translate.put('A', new PatternFormat("EEEE", false) );
        translate.put('b', new PatternFormat("MMM", false) );
        translate.put('B', new PatternFormat("MMMM", false));
        translate.put('c', new PatternFormat("EEE MMM d HH:mm:ss yyyy", true) );
        translate.put('C', new CenturyDateFormat());
        translate.put('d', new PatternFormat("dd", false));
        translate.put('D', new PatternFormat("MM/dd/yy", true) );

        translate.put('e', new PatternFormat("d", false) );
        translate.put('F', new PatternFormat("yyyy-MM-dd", true) );
        translate.put('g', new PatternFormat("yy", false) );
        translate.put('G', new PatternFormat("yyyy", false) );
        translate.put('h', new PatternFormat("MMM", false) );
        translate.put('H', new PatternFormat("HH", false));
        translate.put('I', new PatternFormat("hh", false));
        translate.put('j', new FromTemporalFieldWithPaddingFormat(ChronoField.DAY_OF_YEAR, new Padding('0', 3)));
        translate.put('k', new FromTemporalFieldWithPaddingFormat(ChronoField.HOUR_OF_DAY, new Padding(' ', 2)));
        translate.put('l', new FromTemporalFieldWithPaddingFormat(ChronoField.HOUR_OF_AMPM, new Padding(' ', 2)));
        translate.put('L', new FromTemporalFieldWithPaddingFormat(ChronoField.MILLI_OF_SECOND, new Padding('0', 3)));
        translate.put('m', new PatternFormat("MM", false));
        translate.put('M', new PatternFormat("mm", false));
        translate.put('n', new LiteralPattern("\n"));
        translate.put('N', new FractionalSecondsFormat());
        translate.put('p', new PatternFormat("a", false));
        translate.put('P', new SwitchCaseDateWrapperFormat(new PatternFormat("a", false), false) );
        translate.put('r', new PatternFormat("hh:mm:ss a", true));
        translate.put('R', new PatternFormat("HH:mm", true));
        translate.put('s', new FromTemporalFieldFormat(ChronoField.INSTANT_SECONDS));
        translate.put('S', new PatternFormat("ss", false));
        translate.put('t', new LiteralPattern("\t"));
        translate.put('T', new PatternFormat("HH:mm:ss", true));
        translate.put('u', new FromTemporalFieldFormat(ChronoField.DAY_OF_WEEK));
        translate.put('U', new WeeksInYearFormat(DayOfWeek.SUNDAY, null, true));
        translate.put('v', new SwitchCaseDateWrapperFormat(new PatternFormat("d-MMM-yyyy", true), true));
        translate.put('V', new ISO8601WeekInYearFormat());
        translate.put('w', new DayOfWeekFormat(DayOfWeek.SUNDAY, 0));
        translate.put('W', new WeeksInYearFormat(DayOfWeek.MONDAY, null, true));
        translate.put('x', new PatternFormat("MM/dd/yy", true));
        translate.put('X', new PatternFormat("HH:mm:ss", true));
        translate.put('y', new PatternFormat("yy", false));
        translate.put('Y', new PatternFormat("yyyy", false));
        translate.put('z', new PatternFormat("Z", false));
        translate.put('Z', new PatternFormat("zzzz", false));
        translate.put('%', new LiteralPattern("%"));
    }

    public HybridFormat getFormatter(int strftimePatternCodepoint) {
        return translate.get((char) strftimePatternCodepoint);
    }

    public boolean isKnownConversion(int codepoint) {
        if (codepoint > Character.MAX_VALUE) {
            return false;
        }
        return translate.containsKey((char) codepoint);
    }

    public static void override(Character conversion, HybridFormat newFormat) {
        translate.put(conversion, newFormat);
    }

}

package ua.co.k.strftime.formatters;

import ua.co.k.strftime.LiteralPattern;
import ua.co.k.strftime.formatters.FromTemporalFieldWithPaddingFormat.Padding;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class DateTimeFormatterFactory {

    final static Map<Character, Function<Locale, HybridFormat>> translate = new HashMap<>();
    static {
        translate.put('a', locale -> new PatternFormat("EEE", locale, false));
        translate.put('A', locale -> new PatternFormat("EEEE", locale, false));
        translate.put('b', locale -> new PatternFormat("MMM", locale, false));
        translate.put('B', locale -> new PatternFormat("MMMM", locale, false));
        translate.put('c', locale -> new PatternFormat("EEE MMM d HH:mm:ss yyyy", locale, true));
        translate.put('C', CenturyDateFormat::new);
        translate.put('d', locale -> new PatternFormat("dd", locale, false));
        translate.put('D', locale -> new PatternFormat("MM/dd/yy", locale, true));

        translate.put('e', locale -> new PatternFormat("d", locale, false));
        translate.put('F', locale -> new PatternFormat("yyyy-MM-dd", locale, true));
        translate.put('g', locale -> new PatternFormat("yy", locale, false));
        translate.put('G', locale -> new PatternFormat("yyyy", locale, false));
        translate.put('h', locale -> new PatternFormat("MMM", locale, false));
        translate.put('H', locale -> new PatternFormat("HH", locale, false));
        translate.put('I', locale -> new PatternFormat("hh", locale, false));
        translate.put('j', locale -> new FromTemporalFieldWithPaddingFormat(ChronoField.DAY_OF_YEAR, new Padding('0', 3)));
        translate.put('k', locale -> new FromTemporalFieldWithPaddingFormat(ChronoField.HOUR_OF_DAY, new Padding(' ', 2)));
        translate.put('l', locale -> new FromTemporalFieldWithPaddingFormat(ChronoField.HOUR_OF_AMPM, new Padding(' ', 2)));
        translate.put('L', locale -> new FromTemporalFieldWithPaddingFormat(ChronoField.MILLI_OF_SECOND, new Padding('0', 3)));
        translate.put('m', locale -> new PatternFormat("MM", locale, false));
        translate.put('M', locale -> new PatternFormat("mm", locale, false));
        translate.put('n', locale -> new LiteralPattern("\n"));
        translate.put('N', locale -> new FractionalSecondsFormat());
        translate.put('p', locale -> new PatternFormat("a", locale, false));
        translate.put('P', locale -> new SwitchCaseDateWrapperFormat(new PatternFormat("a", locale, false), false) );
        translate.put('r', locale -> new PatternFormat("hh:mm:ss a", locale, true));
        translate.put('R', locale -> new PatternFormat("HH:mm", locale, true));
        translate.put('s', locale -> new FromTemporalFieldFormat(ChronoField.INSTANT_SECONDS));
        translate.put('S', locale -> new PatternFormat("ss", locale, false));
        translate.put('t', locale -> new LiteralPattern("\t"));
        translate.put('T', locale -> new PatternFormat("HH:mm:ss", locale, true));
        translate.put('u', locale -> new FromTemporalFieldFormat(ChronoField.DAY_OF_WEEK));
        translate.put('U', locale -> new WeeksInYearFormat(DayOfWeek.SUNDAY, null, true));
        translate.put('v', locale -> new SwitchCaseDateWrapperFormat(new PatternFormat("d-MMM-yyyy", locale, true), true));
        translate.put('V', locale -> new ISO8601WeekInYearFormat());
        translate.put('w', locale -> new DayOfWeekFormat(DayOfWeek.SUNDAY, 0));
        translate.put('W', locale -> new WeeksInYearFormat(DayOfWeek.MONDAY, null, true));
        translate.put('x', locale -> new PatternFormat("MM/dd/yy", locale, true));
        translate.put('X', locale -> new PatternFormat("HH:mm:ss", locale, true));
        translate.put('y', locale -> new PatternFormat("yy", locale, false));
        translate.put('Y', locale -> new PatternFormat("yyyy", locale, false));
        translate.put('z', locale -> new PatternFormat("Z", locale, false));
        translate.put('Z', locale -> new PatternFormat("VV", locale, false));
        translate.put('%', locale -> new LiteralPattern("%"));
    }


    public HybridFormat getFormatter(int strftimePatternCodepoint, Locale locale) {
        Function<Locale, HybridFormat> hybridFormatFunction = translate.get((char) strftimePatternCodepoint);
        if (hybridFormatFunction == null) {
            return null;
        }
        return hybridFormatFunction.apply(locale);
    }

    public boolean isKnownConversion(int codepoint) {
        if (codepoint > Character.MAX_VALUE) {
            return false;
        }
        return translate.containsKey((char) codepoint);
    }
}

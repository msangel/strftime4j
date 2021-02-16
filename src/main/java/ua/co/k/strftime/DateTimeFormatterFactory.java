package ua.co.k.strftime;

import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

class DateTimeFormatterFactory {

    final static Map<Character, Function<Locale, HybridFormat>> translate = new HashMap<>();
    static {
        translate.put('a', locale -> new HybridFormat("EEE", locale));
        translate.put('A', locale -> new HybridFormat("EEEE", locale));
        translate.put('b', locale -> new HybridFormat("MMM", locale));
        translate.put('B', locale -> new HybridFormat("MMMM", locale));
        translate.put('c', locale -> new HybridFormat("EEE MMM d HH:mm:ss yyyy", locale));
        translate.put('C', CenturyDateFormat::new);
        translate.put('d', locale -> new HybridFormat("dd", locale));
        translate.put('D', locale -> new HybridFormat("MM/dd/yy", locale));

        translate.put('e', locale -> new HybridFormat("dd", locale)); //will show as '03' instead of ' 3'
        translate.put('F', locale -> new HybridFormat("yyyy-MM-dd", locale));
        translate.put('g', locale -> new HybridFormat("yy", locale));
        translate.put('G', locale -> new HybridFormat("yyyy", locale));
        translate.put('h', locale -> new HybridFormat("MMM", locale));
        translate.put('H', locale -> new HybridFormat("HH", locale));
        translate.put('I', locale -> new HybridFormat("hh", locale));
        translate.put('j', locale -> new HybridFormat("DDD", locale));
        translate.put('k', locale -> new HybridFormat("HH", locale)); //will show as '07' instead of ' 7'
        translate.put('l', locale -> new HybridFormat("hh", locale)); //will show as '07' instead of ' 7'
        translate.put('m', locale -> new HybridFormat("MM", locale));
        translate.put('M', locale -> new HybridFormat("mm", locale));
        translate.put('n', locale -> new LiteralPattern("\n"));
        translate.put('p', locale -> new HybridFormat("a", locale));
        translate.put('P', locale -> new SwitchCaseDateFormatWrapper(new HybridFormat("a", locale), true) );
        translate.put('r', locale -> new HybridFormat("hh:mm:ss a", locale));
        translate.put('R', locale -> new HybridFormat("HH:mm", locale));
        translate.put('s', locale -> new FromTemporalFieldFormat(ChronoField.INSTANT_SECONDS));
        translate.put('S', locale -> new HybridFormat("ss", locale));
        translate.put('t', locale -> new LiteralPattern("\t"));
        translate.put('T', locale -> new HybridFormat("HH:mm:ss", locale));
        translate.put('u', locale -> new FromTemporalFieldFormat(ChronoField.DAY_OF_WEEK));
        // week in year with first Sunday as first day
        translate.put('U', locale -> new FromTemporalFieldFormat(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        translate.put('V', locale -> new HybridFormat("ww", locale));
        // week in year with first Monday as first day
        translate.put('W', locale -> new FromTemporalFieldFormat(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        // ???
        translate.put('w', locale -> new HybridFormat("E", locale));
        translate.put('x', locale -> new HybridFormat("MM/dd/yy", locale));
        translate.put('X', locale -> new HybridFormat("HH:mm:ss", locale));
        translate.put('y', locale -> new HybridFormat("yy", locale));
        translate.put('Y', locale -> new HybridFormat("yyyy", locale));
        translate.put('z', locale -> new HybridFormat("Z", locale));
        translate.put('Z', locale -> new HybridFormat("z", locale));
        translate.put('%', locale -> new LiteralPattern("%"));
    }


    public HybridFormat getFormatter(int strftimePatternCodepoint, Locale locale) {
        return translate.get((char)strftimePatternCodepoint).apply(locale);
    }

    public boolean isKnownConversion(int codepoint) {
        return true;
    }
}

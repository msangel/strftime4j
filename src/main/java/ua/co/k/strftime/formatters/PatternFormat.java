package ua.co.k.strftime.formatters;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

class PatternFormat extends HybridFormat {
    protected final String pattern;
    protected final Locale locale;
    private DateTimeFormatter java8Formatter;

    public PatternFormat(String pattern, Locale locale, boolean combination) {
        super(combination);
        this.pattern = pattern;
        this.locale = locale;
    }

    protected String doFormat(Object obj, int padWidth, boolean strict) {
        if (java8Formatter == null) {
            java8Formatter = DateTimeFormatter.ofPattern(pattern, locale);
        }
        TemporalAccessor temporalAccessor = (TemporalAccessor) obj;
        String res;
        try {
            res = java8Formatter.format(temporalAccessor);
        } catch (RuntimeException e) {
            if (strict) {
                throw e;
            }
            return "";
        }
        return res;
    }
}

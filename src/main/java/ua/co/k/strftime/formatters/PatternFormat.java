package ua.co.k.strftime.formatters;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

class PatternFormat extends HybridFormat {
    protected final String pattern;

    public PatternFormat(String pattern, boolean combination) {
        super(combination);
        this.pattern = pattern;
    }

    protected String doFormat(Object obj, int padWidth, boolean strict, Locale locale) {
        TemporalAccessor temporalAccessor = (TemporalAccessor) obj;
        String res;
        try {
            res = DateTimeFormatter.ofPattern(pattern, locale).format(temporalAccessor);
        } catch (RuntimeException e) {
            if (strict) {
                throw e;
            }
            return "";
        }
        return res;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatternFormat{");
        sb.append("pattern='").append(pattern).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

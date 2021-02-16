package ua.co.k.strftime;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class HybridFormat extends Format {

    private final String pattern;
    private final Locale locale;
    private DateTimeFormatter   java8Formatter;
    private Format              java7Formatter;


    public HybridFormat(String pattern, Locale locale) {
        this.pattern = pattern;
        this.locale = locale;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof TemporalAccessor) {
            if (java8Formatter == null) {
                java8Formatter = DateTimeFormatter.ofPattern(pattern, locale);
            }
            java8Formatter.formatTo((TemporalAccessor)obj, toAppendTo);
        } else {
            Format formatter;
            if (java7Formatter == null) {
                java7Formatter = new SimpleDateFormat(pattern, locale);
            }
            // made this thread-safe again
            formatter = (Format)java7Formatter.clone();
            formatter.format(obj, toAppendTo, pos);
        }
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
}

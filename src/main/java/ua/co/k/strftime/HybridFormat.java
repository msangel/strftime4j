package ua.co.k.strftime;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class HybridFormat {

    protected final String pattern;
    protected final Locale locale;
    private DateTimeFormatter   java8Formatter;

    public HybridFormat(String pattern, Locale locale) {
        this.pattern = pattern;
        this.locale = locale;
    }

    public StringBuilder formatTo(Object obj, StringBuilder toAppendTo) {
        if (obj instanceof Date) {
            obj = ((Date) obj).toInstant().atZone(ZoneId.systemDefault());
        } else if (obj instanceof Calendar) {
            ZoneId zoneId = toZoneId(((Calendar) obj).getTimeZone().getID());
            obj = ((Calendar) obj).toInstant().atZone(zoneId);
        }
        return doFormat(obj, toAppendTo);
    }

    protected StringBuilder doFormat(Object obj, StringBuilder toAppendTo) {
        if (java8Formatter == null) {
            java8Formatter = DateTimeFormatter.ofPattern(pattern, locale);
        }
        java8Formatter.formatTo((TemporalAccessor)obj, toAppendTo);
        return toAppendTo;
    }

    private ZoneId toZoneId(String id) {
        if (id.length() == 3) {
            if ("EST".equals(id))
                return ZoneId.of("America/New_York");
            if ("MST".equals(id))
                return ZoneId.of("America/Denver");
            if ("HST".equals(id))
                return ZoneId.of("America/Honolulu");
        }
        return ZoneId.of(id, ZoneId.SHORT_IDS);
    }
}

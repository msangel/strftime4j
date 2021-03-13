package ua.co.k.strftime.formatters;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

class ZoneOffsetFormat extends HybridFormat {

    private final int zoneColumns;

    public ZoneOffsetFormat() {
        super(false);
        this.zoneColumns = 0;
    }

    public ZoneOffsetFormat(int zoneColumns) {
        super(false);
        this.zoneColumns = zoneColumns;
    }

    @Override
    protected String doFormat(Object obj, int padWidth, boolean strict, Locale locale) {
        if (!(obj instanceof TemporalAccessor)) {
            if (strict) {
                throw FromTemporalFieldFormat.invalidType(obj);
            } else {
                return "";
            }
        }
        TemporalAccessor tObj = (TemporalAccessor) obj;

        if (!tObj.isSupported(ChronoField.OFFSET_SECONDS) && !strict) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter;
        if (zoneColumns == 0) {
            dateTimeFormatter = new DateTimeFormatterBuilder().appendOffset("+HHMM", "+0000").toFormatter(locale);
        } else if (zoneColumns == 1) {
            dateTimeFormatter = new DateTimeFormatterBuilder().appendOffset("+HH:MM", "+00:00").toFormatter(locale);
        } else if (zoneColumns == 2) {
            dateTimeFormatter = new DateTimeFormatterBuilder().appendOffset("+HH:MM:ss", "+00:00:00").toFormatter(locale);
        } else {
            throw new RuntimeException("zoneColumns size is bigger then 2");
        }
        return dateTimeFormatter.format(tObj);
    }
}

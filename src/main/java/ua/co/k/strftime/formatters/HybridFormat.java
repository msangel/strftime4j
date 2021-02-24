package ua.co.k.strftime.formatters;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public abstract class HybridFormat {

    private final boolean combination;

    public HybridFormat(boolean combination) {
        this.combination = combination;
    }

    public boolean isCombination() {
        return combination;
    }

    public String formatTo(Object obj, int width) {
        if (obj instanceof Date) {
            obj = ((Date) obj).toInstant().atZone(ZoneId.systemDefault());
        } else if (obj instanceof Calendar) {
            ZoneId zoneId = toZoneId(((Calendar) obj).getTimeZone().getID());
            obj = ((Calendar) obj).toInstant().atZone(zoneId);
        }
        return doFormat(obj, width);
    }

    protected String doFormat(Object obj, int padWidth) {
        throw new UnsupportedOperationException();
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

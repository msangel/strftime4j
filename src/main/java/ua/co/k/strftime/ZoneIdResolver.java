package ua.co.k.strftime;

import java.time.ZoneId;

public abstract class ZoneIdResolver {
    public static final ZoneIdResolver DEFAULT = new ZoneIdResolver() {
        @Override
        public ZoneId toZoneId(String id) {
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
    };

    public abstract ZoneId toZoneId(String id);
}

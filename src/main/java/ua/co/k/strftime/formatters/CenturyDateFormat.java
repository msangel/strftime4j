package ua.co.k.strftime.formatters;

import java.util.Locale;

/**
 * Lets make clear: years from 2001 to 2100 is 21th century.
 */
class CenturyDateFormat extends PatternFormat {

    public CenturyDateFormat() {
        super("yyyy", false);
    }

    @Override
    public String formatTo(Object obj, int width, boolean strict, Locale locale) {
        String res = super.formatTo(obj, width, strict, locale);
        if (res.isEmpty()) {
            return res;
        }
        int century = Integer.parseInt(res.substring(0, 2)) + 1;
        return String.valueOf(century);
    }
}

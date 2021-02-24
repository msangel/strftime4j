package ua.co.k.strftime.formatters;

import java.util.Locale;

/**
 * Lets make clear: years from 2001 to 2100 is 21th century.
 */
class CenturyDateFormat extends PatternFormat {

    public CenturyDateFormat(Locale locale) {
        super("yyyy", locale, false);
    }

    @Override
    public String formatTo(Object obj, int width) {
        String res = super.formatTo(obj, width);
        int century = Integer.parseInt(res.substring(0, 2)) + 1;
        return String.valueOf(century);
    }
}

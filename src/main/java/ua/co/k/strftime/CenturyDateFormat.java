package ua.co.k.strftime;

import java.util.Locale;

/**
 * Lets make clear: years from 2001 to 2100 is 21th century.
 */
class CenturyDateFormat extends HybridFormat {
    public CenturyDateFormat(Locale locale) {
        super("yyyy", locale);
    }

    @Override
    public String formatTo(Object obj) {
        String res = super.formatTo(obj);
        int century = Integer.parseInt(res.substring(0, 2)) - 1;
        return String.valueOf(century);
    }
}

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
    public StringBuilder formatTo(Object obj, StringBuilder toAppendTo) {
        StringBuilder res = super.formatTo(obj, new StringBuilder());
        int century = Integer.parseInt(res.substring(0, 2)) - 1;
        return toAppendTo.append(century);
    }
}

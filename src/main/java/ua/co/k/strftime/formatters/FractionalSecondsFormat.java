package ua.co.k.strftime.formatters;

import java.time.temporal.ChronoField;
import java.util.Locale;

class FractionalSecondsFormat extends FromTemporalFieldFormat {
    public FractionalSecondsFormat() {
        super(ChronoField.MILLI_OF_SECOND);
    }

    @Override
    protected String doFormat(Object obj, int width, boolean strict, Locale locale) {
        String original = super.doFormat(obj, width, strict, locale);
        if (original.isEmpty() && !strict) {
            return "";
        }
        if (width == 0) {
            width = 9;
        }
        return HybridFormat.padRight(original, width, '0');
    }

}

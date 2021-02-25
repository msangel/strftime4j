package ua.co.k.strftime.formatters;

import java.time.temporal.ChronoField;

class FractionalSecondsFormat extends FromTemporalFieldFormat {
    public FractionalSecondsFormat() {
        super(ChronoField.MILLI_OF_SECOND);
    }

    @Override
    protected String doFormat(Object obj, int width, boolean strict) {
        String original = super.doFormat(obj, width, strict);
        if (width == 0) {
            width = 9;
        }
        return HybridFormat.padRight(original, width, '0');
    }

}

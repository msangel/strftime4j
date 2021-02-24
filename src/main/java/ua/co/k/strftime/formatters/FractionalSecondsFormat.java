package ua.co.k.strftime.formatters;

import ua.co.k.strftime.StrftimeFormatter;

import java.time.temporal.ChronoField;

class FractionalSecondsFormat extends FromTemporalFieldFormat {
    public FractionalSecondsFormat() {
        super(ChronoField.MILLI_OF_SECOND);
    }

    @Override
    protected String doFormat(Object obj, int width) {
        String original = super.doFormat(obj, width);
        if (width == 0) {
            width = 9;
        }
        return StrftimeFormatter.padRight(original, width, '0');
    }
}

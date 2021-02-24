package ua.co.k.strftime.formatters;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

class FromTemporalFieldFormat extends HybridFormat {
    private final TemporalField field;

    public FromTemporalFieldFormat(TemporalField field) {
        super(false);
        this.field = field;
    }

    @Override
    protected String doFormat(Object obj, int width) {
        if (!(obj instanceof TemporalAccessor)) {
            throw new UnsupportedOperationException("Unable to get seconds from unknown type variable: " + obj.getClass());
        }

        long value = doFormat((TemporalAccessor) obj);
        return String.valueOf(value);
    }

    protected Long doFormat(TemporalAccessor obj) {
        return obj.getLong(field);
    }
}

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
    protected String doFormat(Object obj, int width, boolean strict) {
        if (!(obj instanceof TemporalAccessor)) {
            if (strict) {
                throw new UnsupportedOperationException("Unable to get seconds from unknown type variable: " + obj.getClass());
            } else {
                return "";
            }
        }

        Long value = doFormat((TemporalAccessor) obj, strict);
        if (!strict && value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    protected Long doFormat(TemporalAccessor obj, boolean strict) {
        if (!obj.isSupported(field) && !strict) {
            return null;
        }
        return obj.getLong(field);
    }
}

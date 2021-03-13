package ua.co.k.strftime.formatters;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Locale;

class FromTemporalFieldFormat extends HybridFormat {
    private final TemporalField field;

    public FromTemporalFieldFormat(TemporalField field) {
        super(false);
        this.field = field;
    }

    @Override
    protected String doFormat(Object obj, int width, boolean strict, Locale locale) {
        if (!(obj instanceof TemporalAccessor)) {
            if (strict) {
                throw invalidType(obj);
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

    public static UnsupportedOperationException invalidType(Object obj){
        return new UnsupportedOperationException("Unable to get temporal field from unknown type variable: " + obj.getClass());
    }
}

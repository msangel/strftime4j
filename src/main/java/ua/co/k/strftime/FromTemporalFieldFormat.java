package ua.co.k.strftime;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Locale;

public class FromTemporalFieldFormat extends HybridFormat {
    private final TemporalField field;

    public FromTemporalFieldFormat(TemporalField field) {
        super(null, null);
        this.field = field;
    }

    @Override
    protected StringBuilder doFormat(Object obj, StringBuilder toAppendTo) {
        if (!(obj instanceof TemporalAccessor)) {
            throw new UnsupportedOperationException("Unable to get seconds from unknown type variable: " + obj.getClass());
        }
        int value = ((TemporalAccessor) obj).get(field);
        return toAppendTo.append(value);
    }
}

package ua.co.k.strftime.formatters;

import java.time.temporal.TemporalField;

class FromTemporalFieldWithPaddingFormat extends FromTemporalFieldFormat {
    private final Padding padding;

    public static class Padding {
        final char symbol;
        final int count;
        public Padding(char symbol, int count) {
            this.symbol = symbol;
            this.count = count;
        }
    }
    public FromTemporalFieldWithPaddingFormat(TemporalField field, Padding padding) {
        super(field);
        this.padding = padding;
    }

    @Override
    protected String doFormat(Object obj, int width, boolean strict) {
        String res = super.doFormat(obj, width, strict);
        return HybridFormat.padLeft(res, padding.count, padding.symbol);
    }
}

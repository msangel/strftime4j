package ua.co.k.strftime;

class SwitchCaseDateFormatWrapper extends HybridFormat {
    private final HybridFormat delegate;
    private final boolean toUpperCase;

    public SwitchCaseDateFormatWrapper(HybridFormat delegate, boolean toUpperCase) {
        super(delegate.pattern, delegate.locale);
        this.delegate = delegate;
        this.toUpperCase = toUpperCase;
    }

    @Override
    public StringBuilder formatTo(Object obj, StringBuilder toAppendTo) {
        String initial = delegate.formatTo(obj, new StringBuilder()).toString();
        if (toUpperCase) {
            initial = initial.toUpperCase(locale);
        } else {
            initial = initial.toLowerCase(locale);
        }
        return toAppendTo.append(initial);
    }
}

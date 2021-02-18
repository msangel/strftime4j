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
    public String formatTo(Object obj) {
        String initial = delegate.formatTo(obj).toString();
        if (toUpperCase) {
            initial = initial.toUpperCase(locale);
        } else {
            initial = initial.toLowerCase(locale);
        }
        return initial;
    }
}

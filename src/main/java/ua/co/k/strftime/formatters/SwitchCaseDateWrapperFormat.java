package ua.co.k.strftime.formatters;

class SwitchCaseDateWrapperFormat extends PatternFormat {
    private final HybridFormat delegate;
    private final boolean toUpperCase;

    public SwitchCaseDateWrapperFormat(PatternFormat delegate, boolean toUpperCase) {
        super(delegate.pattern, delegate.locale, delegate.isCombination());
        this.delegate = delegate;
        this.toUpperCase = toUpperCase;
    }

    @Override
    public String formatTo(Object obj, int width, boolean strict) {
        String initial = delegate.formatTo(obj, width, strict);
        if (toUpperCase) {
            initial = initial.toUpperCase(locale);
        } else {
            initial = initial.toLowerCase(locale);
        }
        return initial;
    }
}

package ua.co.k.strftime.formatters;

import ua.co.k.strftime.ZoneIdResolver;

import java.util.Locale;

class SwitchCaseDateWrapperFormat extends PatternFormat {
    private final HybridFormat delegate;
    private final boolean toUpperCase;

    public SwitchCaseDateWrapperFormat(PatternFormat delegate, boolean toUpperCase) {
        super(delegate.pattern, delegate.isCombination());
        this.delegate = delegate;
        this.toUpperCase = toUpperCase;
    }

    @Override
    public String formatTo(Object obj, int width, boolean strict, Locale locale, ZoneIdResolver zoneIdResolver) {
        String initial = delegate.formatTo(obj, width, strict, locale, zoneIdResolver);
        if (toUpperCase) {
            initial = initial.toUpperCase(locale);
        } else {
            initial = initial.toLowerCase(locale);
        }
        return initial;
    }
}

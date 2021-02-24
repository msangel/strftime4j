package ua.co.k.strftime;

import ua.co.k.strftime.formatters.HybridFormat;

public class LiteralPattern extends HybridFormat {
    private final String literal;

    public LiteralPattern(String literal) {
        super(false);
        this.literal = literal;
    }

    @Override
    public String formatTo(Object obj, int width) {
        return literal;
    }
}

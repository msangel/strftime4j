package ua.co.k.strftime;

class LiteralPattern extends HybridFormat {
    private final String literal;

    public LiteralPattern(String literal) {
        super(null, null);
        this.literal = literal;
    }

    @Override
    public String formatTo(Object obj) {
        return literal;
    }
}

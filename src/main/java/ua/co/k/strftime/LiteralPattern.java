package ua.co.k.strftime;

class LiteralPattern extends HybridFormat {
    private final String literal;

    public LiteralPattern(String literal) {
        super(null, null);
        this.literal = literal;
    }

    @Override
    public StringBuilder formatTo(Object obj, StringBuilder toAppendTo) {
        return toAppendTo.append(literal);
    }
}

package ua.co.k.strftime;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

class ValueToken implements Token {

    final static List<Integer> flags = new ArrayList<>(6);
    {
        flags.add((int) '-'); // -  don't pad a numerical output.
        flags.add((int) '_'); // _  use spaces for padding.
        flags.add((int) '0'); // 0  use zeros for padding.
        flags.add((int) '^'); // ^  upcase the result string.
        flags.add((int) '#'); // #  change case
        flags.add((int) ':'); // :  use colons for %z
    }

    private int conversion;

    enum Parts {
        // %<flags><width><modifier><conversion>
        flags,
        width,
        modifier,
        conversion
    }

    Parts current;

    public ValueToken() {
    }

    public static boolean isFlag(int codepoint) {
        return flags.contains(codepoint);
    }

    public static boolean isWidth(int codepoint) {
        return Character.isDigit(codepoint);
    }

    /**
     * The modifiers are “E” and “O”. They are ignored.
     */
    public static boolean isModifier(int codepoint) {
        return 'E' == codepoint || 'O' == codepoint;
    }

    public static boolean isConversion(int codepoint) {
        return true;
    }

    public void addFlag(int codepoint) {

    }

    public void addWidth(int codepoint) {

    }

    public void setConversion(int codepoint) {
        conversion = codepoint;
    }

    @Override
    public void render(ZonedDateTime date, StringBuilder out) {
        out.appendCodePoint('<');
        out.appendCodePoint(conversion);
        out.appendCodePoint('>');
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValueToken{");
        sb.appendCodePoint(conversion);
        sb.append('}');
        return sb.toString();
    }
}

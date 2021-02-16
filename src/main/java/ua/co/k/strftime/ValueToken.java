package ua.co.k.strftime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ValueToken implements Token {

    private static DateTimeFormatterFactory factory = new DateTimeFormatterFactory();

    final static List<Integer> flags = new ArrayList<>(6);
    private final Locale locale;
    private DateTimeFormatter dateTimeFormatter;

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

    public ValueToken(Locale locale) {
        this.locale = locale;
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
        return factory.isKnownConversion(codepoint);
    }

    public void addFlag(int codepoint) {

    }

    public void addWidth(int codepoint) {

    }

    public void applyConversion(int codepoint) {
        conversion = codepoint;
        this.dateTimeFormatter = factory.getDateTimeFormatter(codepoint, locale);
    }

    @Override
    public void render(ZonedDateTime date, StringBuilder out) {
        dateTimeFormatter.formatTo(date, out);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValueToken{");
        sb.appendCodePoint(conversion);
        sb.append('}');
        return sb.toString();
    }
}

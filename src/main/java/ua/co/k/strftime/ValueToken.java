package ua.co.k.strftime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

class ValueToken implements Token {

    private static DateTimeFormatterFactory factory = new DateTimeFormatterFactory();

    final static List<Integer> flags = new ArrayList<>(6);
    private final Locale locale;
    private HybridFormat formatter;
    private int zoneColumns = 0;
    private List<Character> widthRaw = new ArrayList<>();

    enum FlagCaseMode {
        none, upper, change
    }
    private FlagCaseMode flagCaseMode = FlagCaseMode.none;

    enum FlagPadMode {
        flagPadModeNone,
        flagPadModeZeros,
        flagPadModeSpaces
    }
    private FlagPadMode flagPadMode = FlagPadMode.flagPadModeNone;

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
        Objects.requireNonNull(locale);
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
        switch (codepoint) {
            case '-':
                flagPadMode = FlagPadMode.flagPadModeNone;
                break;
            case '0':
                flagPadMode = FlagPadMode.flagPadModeZeros;
                break;
            case '_':
                flagPadMode = FlagPadMode.flagPadModeSpaces;
                break;
            case '#':
                flagCaseMode = FlagCaseMode.change;
                break;
            case '^':
                flagCaseMode = FlagCaseMode.upper;
                break;
            case ':':
                zoneColumns++;
                break;
        }
    }

    public void addWidth(int codepoint) {
        widthRaw.add((char)codepoint);
    }

    public boolean applyConversion(int codepoint) {
        if (codepoint != 'z' && zoneColumns > 0) {
            return false;
        }
        conversion = codepoint;
        this.formatter = factory.getFormatter(codepoint, locale);
        return true;
    }

    @Override
    public void render(Object date, StringBuilder out) {
        String formatted = formatter.formatTo(date);
        int padWidth = 0;
        if (!widthRaw.isEmpty()) {
            padWidth = Integer.parseInt(widthRaw.stream().map(Object::toString).collect(Collectors.joining()));
        }
        switch (flagPadMode) {
            case flagPadModeNone:
                formatted = removeLeadingZeros(formatted);
                break;
            case flagPadModeZeros:
                formatted = padWithZeros(formatted, padWidth);
                break;
            case flagPadModeSpaces:
                formatted = padWithSpaces(formatted, padWidth);
                break;
        }
        switch (flagCaseMode) {
            case none:
                break;
            case upper:
            case change:
                formatted = formatted.toUpperCase(locale);
        }
        out.append(formatted);
    }

    private String padWithSpaces(String formatted, int padWidth) {
        return padLeft(formatted, padWidth, ' ');
    }

    private String padWithZeros(String formatted, int padWidth) {
        return padLeft(formatted, padWidth, '0');
    }

    private String removeLeadingZeros(String formatted) {
        return formatted;
    }
    public String padLeft(String inputString, int length, char padSymbol) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(padSymbol);
        }
        sb.append(inputString);

        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValueToken{");
        sb.appendCodePoint(conversion);
        sb.append('}');
        return sb.toString();
    }
}

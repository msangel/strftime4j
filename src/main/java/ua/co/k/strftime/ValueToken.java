package ua.co.k.strftime;

import ua.co.k.strftime.formatters.DateTimeFormatterFactory;
import ua.co.k.strftime.formatters.HybridFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static ua.co.k.strftime.formatters.HybridFormat.padWithSpaces;
import static ua.co.k.strftime.formatters.HybridFormat.padWithZeros;
import static ua.co.k.strftime.formatters.HybridFormat.removeLeadingZeros;

class ValueToken implements Token {

    private static final DateTimeFormatterFactory factory = new DateTimeFormatterFactory();

    final static List<Integer> flags = new ArrayList<>(6);
    {
        flags.add((int) '-'); // -  don't pad a numerical output.
        flags.add((int) '_'); // _  use spaces for padding.
        flags.add((int) '0'); // 0  use zeros for padding.
        flags.add((int) '^'); // ^  upcase the result string.
        flags.add((int) '#'); // #  change case
        flags.add((int) ':'); // :  use colons for %z
    }

    private HybridFormat formatter;
    private int zoneColumns = 0;
    private final List<Character> widthRaw = new ArrayList<>();

    enum FlagCaseMode {
        none, upper, change
    }
    private FlagCaseMode flagCaseMode = FlagCaseMode.none;

    enum FlagPadMode {
        flagPadModeDefaults,
        flagPadModeNone,
        flagPadModeZeros,
        flagPadModeSpaces
    }
    private FlagPadMode flagPadMode = FlagPadMode.flagPadModeDefaults;



    private int conversion;

    enum Parts {
        // %<flags><width><modifier><conversion>
        flags,
        width,
        modifier,
        conversion
    }

    Parts current;

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
        HybridFormat targetFormatter = factory.getFormatter(codepoint);
        if (targetFormatter == null) {
            // unknown formatter, safe fallback
            return false;
        }
        if (targetFormatter instanceof LiteralPattern) {
            // flags for literal patterns treats as invalid pattern
            // and so - fallback to text token
            if (flagPadMode != FlagPadMode.flagPadModeDefaults || flagCaseMode != FlagCaseMode.none) {
                return false;
            }
        }
        this.formatter = targetFormatter;

        return true;
    }

    @Override
    public void render(Object date, StringBuilder out, boolean strict, Locale locale) {
        int padWidth = 0;
        if (!widthRaw.isEmpty()) {
            padWidth = Integer.parseInt(widthRaw.stream().map(Object::toString).collect(Collectors.joining()));
        }
        String formatted = formatter.formatTo(date, padWidth, strict, locale);
        if (!formatter.isCombination()) {
            switch (flagPadMode) {
                case flagPadModeNone:
                    formatted = removeLeadingZeros(formatted);
                    break;
                case flagPadModeZeros:
                    formatted = padWithZeros(formatted, padWidth);
                    break;
                case flagPadModeSpaces:
                    formatted = padWithSpaces(removeLeadingZeros(formatted), Math.max(padWidth, 2));
                    break;
                case flagPadModeDefaults:
                default:
                    // do nothing
            }
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValueToken{");
        sb.appendCodePoint(conversion);
        sb.append('}');
        return sb.toString();
    }
}

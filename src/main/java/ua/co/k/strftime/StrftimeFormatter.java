package ua.co.k.strftime;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StrftimeFormatter {
// todo: 1) :::z, 2) symbol redefining public
    private final Locale locale;
    private final List<Token> tokens;
    private final boolean strict;

    public StrftimeFormatter(String pattern, Locale locale, boolean strict) {
        this.locale = locale;
        this.strict = strict;
        this.tokens = parse(pattern);
    }

    StrftimeFormatter(List<Token> tokens, Locale locale, boolean strict) {
        this.locale = locale;
        this.tokens = tokens;
        this.strict = strict;
    }

    public static StrftimeFormatter ofSafePattern(String pattern) {
        return new StrftimeFormatter(pattern, Locale.getDefault(), false);
    }

    public static StrftimeFormatter ofSafePattern(String pattern, Locale locale) {
        return new StrftimeFormatter(pattern, locale, false);
    }

    public static StrftimeFormatter ofStrictPattern(String pattern) {
        return new StrftimeFormatter(pattern, Locale.getDefault(), true);
    }

    public static StrftimeFormatter ofStrictPattern(String pattern, Locale locale) {
        return new StrftimeFormatter(pattern, locale, true);
    }

    public StrftimeFormatter withLocale(Locale locale) {
        if (this.locale.equals(locale)) {
            return this;
        }
        return new StrftimeFormatter(tokens, locale, strict);
    }

    public String format(Object obj) {
        StringBuilder sb = new StringBuilder();
        formatTo(obj, sb);
        return sb.toString();
    }

    public void formatTo(Object obj, StringBuilder sb) {
        tokens.forEach( el -> el.render(obj, sb, strict, locale));
    }

    private List<Token> parse(String in) {
        List<Integer> codepoints = in.codePoints().boxed().collect(Collectors.toList());
        ParseContext parseContext = new ParseContext(codepoints);
        List<Token> res = new ArrayList<>();
        while (moreTokens(parseContext)) {
            Token token = readNext(parseContext);
            res.add(token);
        }
        return res;
    }


    static class ParseContext {
        enum State {
            TEXT,
            VALUE
        }
        final List<Integer> codepoints;
        int startOffset = 0;
        State state = State.TEXT;
        int valueStaredPosition;

        ParseContext(List<Integer> codepoints) {
            this.codepoints = codepoints;
        }
    }

    private Token readNext(ParseContext parseContext) {
        if (parseContext.state == ParseContext.State.TEXT) {
            return readNextText(parseContext);
        } else {
            return readNextValue(parseContext);
        }
    }


    private Token readNextValue(ParseContext parseContext) {
        ValueToken token = new ValueToken();
        List<Integer> fallback = new ArrayList<>();
        // %<flags><width><modifier><conversion>
        token.current = ValueToken.Parts.flags;
        for(; parseContext.startOffset < parseContext.codepoints.size(); ++parseContext.startOffset) {
            int codepoint = parseContext.codepoints.get(parseContext.startOffset);
            fallback.add(codepoint);
            if (token.current == ValueToken.Parts.flags) {
                if (ValueToken.isFlag(codepoint)) {
                    token.addFlag(codepoint);
                    continue;
                } else if (ValueToken.isWidth(codepoint)) {
                    token.current = ValueToken.Parts.width;
                } else if (ValueToken.isModifier(codepoint)) {
                    token.current = ValueToken.Parts.modifier;
                } else if (ValueToken.isConversion(codepoint)) {
                    token.current = ValueToken.Parts.conversion;
                }
            }
            if (token.current == ValueToken.Parts.width) {
                if (ValueToken.isWidth(codepoint)) {
                    token.addWidth(codepoint);
                    continue;
                } else if (ValueToken.isModifier(codepoint)) {
                    token.current = ValueToken.Parts.modifier;
                } else if (ValueToken.isConversion(codepoint)) {
                    token.current = ValueToken.Parts.conversion;
                }
            }
            if (token.current == ValueToken.Parts.modifier) {
                if (ValueToken.isModifier(codepoint)) {
                    // not supported
                    // dead end, fallback
                    ++parseContext.startOffset;
                    parseContext.state = ParseContext.State.TEXT;
                    return new TextToken(fallback);
                } else if (ValueToken.isConversion(codepoint)) {
                    token.current = ValueToken.Parts.conversion;
                }
            }

            if (token.current == ValueToken.Parts.conversion) {
                if (ValueToken.isConversion(codepoint)) {
                    boolean applied = token.applyConversion(codepoint);
                    ++parseContext.startOffset;
                    if (!applied) {
                        // dead end, fallback
                        parseContext.state = ParseContext.State.TEXT;
                        return new TextToken(fallback);
                    }
                    break;
                }
            }
            // dead end, fallback
            ++parseContext.startOffset;
            parseContext.state = ParseContext.State.TEXT;
            return new TextToken(fallback);
        }
        parseContext.state = ParseContext.State.TEXT;
        return token;
    }

    private Token readNextText(ParseContext parseContext) {
        TextToken token = new TextToken();
        for(; parseContext.startOffset < parseContext.codepoints.size(); ++parseContext.startOffset) {
            int codepoint = parseContext.codepoints.get(parseContext.startOffset);
            if (codepoint == '%') {
                ++parseContext.startOffset;
                if (!moreTokens(parseContext)) {
                    token.add('%');
                    break;
                }
                parseContext.valueStaredPosition = parseContext.startOffset - 1;
                parseContext.state = ParseContext.State.VALUE;
                break;
            } else {
                token.add(codepoint);
            }
        }
        return token;
    }

    private boolean moreTokens(ParseContext parseContext) {
        return parseContext.codepoints.size() > parseContext.startOffset;
    }


}

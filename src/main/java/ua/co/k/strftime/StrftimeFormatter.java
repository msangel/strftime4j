package ua.co.k.strftime;


import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class StrftimeFormatter {

    private static final Map<Locale, StrftimeFormatter> localeMapHolder = new HashMap<>();

    private Locale locale;

    public static StrftimeFormatter ofPattern(String pattern) {
        return new StrftimeFormatter();
    }

    public static StrftimeFormatter ofPattern(String pattern, Locale locale) {
        return new StrftimeFormatter();
    }

    public StrftimeFormatter withLocale(Locale locale) {
        if (this.locale.equals(locale)) {
            return this;
        }
        return new StrftimeFormatter();
    }

    public String format(TemporalAccessor temporal) {
        return null;
    }

    public void formatTo(TemporalAccessor temporal, Appendable appendable) {

    }

    public List<Token> parse(String in) {
        List<Integer> codepoints = in.codePoints().boxed().collect(Collectors.toList());
        ParseContext parseContext = new ParseContext(codepoints);
        List<Token> res = new ArrayList<>();
        while (moreTokens(parseContext)) {
            Token token = readNext(parseContext);
            res.add(token);
        }
        return res;
    }

    public String printStream(List<Token> in) {
        StringBuilder out = new StringBuilder();
        in.forEach( el -> el.render(null, out));
        return out.toString();
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
                    continue;
                } else if (ValueToken.isConversion(codepoint)) {
                    token.current = ValueToken.Parts.conversion;
                }
            }

            if (token.current == ValueToken.Parts.conversion) {
                if (ValueToken.isConversion(codepoint)) {
                    token.setConversion(codepoint);
                    ++parseContext.startOffset;
                    break;
                }
            }
            // dead end, fallback
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

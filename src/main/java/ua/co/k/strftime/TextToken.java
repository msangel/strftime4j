package ua.co.k.strftime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class TextToken implements Token {
    List<Integer> codepoints = new ArrayList<>();

    public TextToken(List<Integer> fallback) {
        fallback.add(0, (int) '%');
        this.codepoints = fallback;
    }

    public TextToken() {

    }

    void add(int codepoint) {
        codepoints.add(codepoint);
    }

    @Override
    public void render(Object date, StringBuilder out, boolean strict, Locale locale) {
        codepoints.forEach(out::appendCodePoint);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TextToken{");
        codepoints.forEach(sb::appendCodePoint);
        sb.append('}');
        return sb.toString();
    }
}

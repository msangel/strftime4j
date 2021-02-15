package ua.co.k.strftime;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public void render(ZonedDateTime date, StringBuilder out) {
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

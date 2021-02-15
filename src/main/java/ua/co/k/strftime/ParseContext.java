package ua.co.k.strftime;

import java.util.List;

class ParseContext {
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

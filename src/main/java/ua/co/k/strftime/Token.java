package ua.co.k.strftime;

import java.util.Locale;

interface Token {
    void render(Object date, StringBuilder out, boolean strict, Locale locale);
}

package ua.co.k.strftime;

interface Token {
    void render(Object date, StringBuilder out);
}

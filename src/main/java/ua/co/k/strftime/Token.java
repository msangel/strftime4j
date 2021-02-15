package ua.co.k.strftime;

import java.time.ZonedDateTime;

interface Token {
    void render(ZonedDateTime date, StringBuilder out);
}

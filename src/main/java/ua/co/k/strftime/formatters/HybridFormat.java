package ua.co.k.strftime.formatters;

import ua.co.k.strftime.ZoneIdResolver;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class HybridFormat {

    private final boolean combination;

    public HybridFormat(boolean combination) {
        this.combination = combination;
    }

    public static String padWithSpaces(String formatted, int padWidth) {
        return padLeft(formatted, padWidth, ' ');
    }

    public static String padWithZeros(String formatted, int padWidth) {
        return padLeft(formatted, padWidth, '0');
    }

    public static String removeLeadingZeros(String formatted) {
        return formatted.replaceFirst("^0+(?!$)", "");
    }

    public static String padRight(String inputString, int length, char padSymbol) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(inputString);
        while (sb.length() < length) {
            sb.append(padSymbol);
        }
        return sb.toString();
    }

    public static String padLeft(String inputString, int length, char padSymbol) {
        if (inputString.length()==0) {
            return inputString;
        }
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

    public boolean isCombination() {
        return combination;
    }

    public String formatTo(Object obj, int width, boolean strict, Locale locale, ZoneIdResolver zoneResolver) {
        if (obj instanceof Date) {
            obj = ((Date) obj).toInstant().atZone(ZoneId.systemDefault());
        } else if (obj instanceof Calendar) {
            String calendarZoneId = ((Calendar) obj).getTimeZone().getID();
            ZoneId zoneId = zoneResolver.toZoneId(calendarZoneId);
            obj = ((Calendar) obj).toInstant().atZone(zoneId);
        }
        return doFormat(obj, width, strict, locale);
    }

    // here is default instead of abstract, because
    // if subclasses will override formatTo method, this one
    // will never be called, and so - implementing
    protected String doFormat(Object obj, int padWidth, boolean strict, Locale locale) {
        throw new UnsupportedOperationException();
    }
}

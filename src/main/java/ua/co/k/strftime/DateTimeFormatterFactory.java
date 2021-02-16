package ua.co.k.strftime;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class DateTimeFormatterFactory {

    final static Map<Character, Function<Locale, HybridFormat>> translate = new HashMap<>();
    static {
        translate.put('a', locale -> new HybridFormat("EEE", locale));
//        translate.put('A', locale -> DateTimeFormatter.ofPattern("EEEE", locale));
//        translate.put('b', locale -> DateTimeFormatter.ofPattern("MMM", locale));
//        translate.put('B', locale -> DateTimeFormatter.ofPattern("MMMM", locale));
//        translate.put('c', locale -> DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy", locale);
//
//        //There's no way to specify the century in SimpleDateFormat.  We don't want to hard-code
//        //20 since this could be wrong for the pre-2000 files.
//        //translate.put("C", "20");
//        translate.put("d","dd");
//        translate.put("D","MM/dd/yy");
//        translate.put("e","dd"); //will show as '03' instead of ' 3'
//        translate.put("F","yyyy-MM-dd");
//        translate.put("g","yy");
//        translate.put("G","yyyy");
//        translate.put("H","HH");
//        translate.put("h","MMM");
//        translate.put("I","hh");
//        translate.put("j","DDD");
//        translate.put("k","HH"); //will show as '07' instead of ' 7'
//        translate.put("l","hh"); //will show as '07' instead of ' 7'
//        translate.put("m","MM");
//        translate.put("M","mm");
//        translate.put("n","\n");
//        translate.put("p","a");
//        translate.put("P","a");  //will show as pm instead of PM
//        translate.put("r","hh:mm:ss a");
//        translate.put("R","HH:mm");
//        //There's no way to specify this with SimpleDateFormat
//        //translate.put("s","seconds since epoch");
//        translate.put("S","ss");
//        translate.put("t","\t");
//        translate.put("T","HH:mm:ss");
//        //There's no way to specify this with SimpleDateFormat
//        //translate.put("u","day of week ( 1-7 )");
//
//        //There's no way to specify this with SimpleDateFormat
//        //translate.put("U","week in year with first Sunday as first day...");
//
//        translate.put("V","ww"); //I'm not sure this is always exactly the same
//
//        //There's no way to specify this with SimpleDateFormat
//        //translate.put("W","week in year with first Monday as first day...");
//
//        //There's no way to specify this with SimpleDateFormat
//        //translate.put("w","E");
//        translate.put("X","HH:mm:ss");
//        translate.put("x","MM/dd/yy");
//        translate.put("y","yy");
//        translate.put("Y","yyyy");
//        translate.put("Z","z");
//        translate.put("z","Z");
//        translate.put("%","%");
    }


    public DateTimeFormatter getDateTimeFormatter(int strftimePatternCodepoint, Locale locale) {
        return DateTimeFormatter.ofPattern("");
    }

    public boolean isKnownConversion(int codepoint) {
        return false;
    }
}

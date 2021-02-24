package ua.co.k.strftime.formatters;

import java.time.DayOfWeek;

class ISO8601WeekInYearFormat extends WeeksInYearFormat {
    /**
     * https://www.epochconverter.com/weeknumbers
     */
    public ISO8601WeekInYearFormat() {
        super(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, false);
    }
}

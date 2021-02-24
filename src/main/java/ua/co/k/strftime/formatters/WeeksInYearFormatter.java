package ua.co.k.strftime.formatters;

import ua.co.k.strftime.StrftimeFormatter;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

public class WeeksInYearFormatter extends HybridFormat {
    private final DayOfWeek firstDayOfWeek;
    private final DayOfWeek mustInclude;

    public WeeksInYearFormatter(DayOfWeek dayOfWeek, DayOfWeek mustInclude) {
        super(false);
        this.firstDayOfWeek = dayOfWeek;
        this.mustInclude = mustInclude;
    }

    @Override
    protected String doFormat(Object obj, int padWidth) {
        if (!(obj instanceof Temporal)) {
            throw new IllegalArgumentException("given parameter is not a Temporal and cannot be adjusted to it");
        }
        Temporal tObj = (Temporal) obj;

        Temporal firstOfThisYear = tObj.with(TemporalAdjusters.firstDayOfYear());
        Temporal startOfFirstWeekInMonth;
        if (mustInclude == null) {
            startOfFirstWeekInMonth = firstOfThisYear.with(TemporalAdjusters.dayOfWeekInMonth(1, firstDayOfWeek));
        } else {
            Temporal firstRequiredDay = firstOfThisYear.with(TemporalAdjusters.dayOfWeekInMonth(1, mustInclude));
            startOfFirstWeekInMonth = firstRequiredDay.with(ChronoField.DAY_OF_WEEK, 1);
        }
        long between = ChronoUnit.WEEKS.between(tObj, startOfFirstWeekInMonth);
        return StrftimeFormatter.padWithZeros(String.valueOf(between), 2);
    }
}

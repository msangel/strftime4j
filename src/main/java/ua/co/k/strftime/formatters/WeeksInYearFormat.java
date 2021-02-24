package ua.co.k.strftime.formatters;

import ua.co.k.strftime.StrftimeFormatter;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

public class WeeksInYearFormat extends HybridFormat {
    private final DayOfWeek firstDayOfWeek;
    private final DayOfWeek mustInclude;
    private final boolean preFirstAsZero;

    public WeeksInYearFormat(DayOfWeek dayOfWeek, DayOfWeek mustInclude, boolean preFirstAsZero) {
        super(false);
        this.firstDayOfWeek = dayOfWeek;
        this.mustInclude = mustInclude;
        this.preFirstAsZero = preFirstAsZero;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String doFormat(Object obj, int padWidth) {
        if (!(obj instanceof Temporal)) {
            throw new IllegalArgumentException("given parameter is not a Temporal and cannot be adjusted to it");
        }
        if(!(obj instanceof Comparable)) {
            throw new IllegalArgumentException("given parameter cannot be compared, and so, it cannot be anything that contains week in it");
        }

        Temporal tObj = (Temporal) obj;

        Temporal firstDayOfThisYear = cast(obj).with(TemporalAdjusters.firstDayOfYear());

        Temporal startOfFirstWeekInMonth = getStartOfFirstWeekInYear(firstDayOfThisYear);

        long between;
        boolean actuallyPreFirst = cast(startOfFirstWeekInMonth).compareTo(cast(obj)) > 0;
        if (actuallyPreFirst && preFirstAsZero) {
            between = 0;
        } else {
            if (actuallyPreFirst) { // preFirstAsZero == false
                int currentYear = firstDayOfThisYear.get(ChronoField.YEAR);
                firstDayOfThisYear = firstDayOfThisYear.with(ChronoField.YEAR, currentYear-1).with(TemporalAdjusters.firstDayOfYear());
                startOfFirstWeekInMonth = getStartOfFirstWeekInYear(firstDayOfThisYear);
            }
            between = ChronoUnit.WEEKS.between(startOfFirstWeekInMonth, tObj) + 1;
        }
        return StrftimeFormatter.padWithZeros(String.valueOf(between), 2);
    }

    private Temporal getStartOfFirstWeekInYear(Temporal firstOfThisYear) {
        Temporal startOfFirstWeekInMonth;
        if (mustInclude == null) {
            startOfFirstWeekInMonth = firstOfThisYear.with(TemporalAdjusters.dayOfWeekInMonth(1, firstDayOfWeek));
        } else {
            Temporal firstRequiredDay = firstOfThisYear.with(TemporalAdjusters.dayOfWeekInMonth(1, mustInclude));
            startOfFirstWeekInMonth = firstRequiredDay.with(ChronoField.DAY_OF_WEEK, 1);
        }
        return startOfFirstWeekInMonth;
    }

    private<T extends Comparable & Temporal> T cast(Object obj) {
        return (T) obj;
    }
}

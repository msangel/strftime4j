package ua.co.k.strftime.formatters;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

class DayOfWeekFormat extends FromTemporalFieldFormat {
    private final DayOfWeek firstDay;
    private final int startCount;


    public DayOfWeekFormat(DayOfWeek firstDay, int startCount) {
        super(ChronoField.DAY_OF_WEEK);
        this.firstDay = firstDay;
        this.startCount = startCount;
    }

    @Override
    protected Long doFormat(TemporalAccessor obj, boolean strict) {
        Long original = super.doFormat(obj, strict); // Mon is 1, Sun is 7
        if (original == null) {
          if (!strict) {
            return null;
          } else {
            Objects.requireNonNull(original);
          }
        }
        // just convert day number from Monday-based system to firstDay-based
        return ((7 + original - firstDay.getValue()) % 7) + startCount;
    }
}

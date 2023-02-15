package telran.time;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

import java.time.DayOfWeek;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		do {
			temporal = temporal.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		} while (temporal.get(DAY_OF_MONTH) != 13);
		return temporal;
	}

}
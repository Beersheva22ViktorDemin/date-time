package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

public class WorkingDays implements TemporalAdjuster {

	private int daysToAdd;
	private DayOfWeek[] dayOffs;
	
	public WorkingDays(int daysToAdd, DayOfWeek[] dayOffs) {
		this.daysToAdd = daysToAdd;
		this.dayOffs = dayOffs;
	}
	
	@Override
	public Temporal adjustInto(Temporal temporal) {
		
        int addedDays = 0;
        while (addedDays < daysToAdd) {
            temporal = temporal.plus(1, ChronoUnit.DAYS);
            if (!isWeekend(temporal)) {
                addedDays++;
            }
        }
        
		return temporal;
	}
	
    private boolean isWeekend(Temporal temporal) {
        DayOfWeek dayOfWeek = DayOfWeek.from(temporal);
        return Arrays.stream(dayOffs).anyMatch(n -> n == dayOfWeek);
    }

}

package telran.time;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,d", Locale.forLanguageTag("he"));
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.DAYS;
		System.out.printf("Number of %s between %s and %s is %d", unit,
				birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
		
	}
	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}
	
	@Test
	void nextFriday13Test () {
		NextFriday13 obj = new NextFriday13();
		assertEquals(LocalDate.parse("2023-10-13"), obj.adjustInto(LocalDate.parse("2023-01-13")));
		assertEquals(LocalDate.parse("2023-10-13"), obj.adjustInto(LocalDate.parse("2023-01-16")));
	}
	
	@Test
	void workingDaysTest () {
		WorkingDays obj = new WorkingDays(1, new DayOfWeek[] { DayOfWeek.FRIDAY, DayOfWeek.SATURDAY });
		assertEquals(LocalDate.parse("2023-02-16"), obj.adjustInto(LocalDate.parse("2023-02-15")));
		assertEquals(LocalDate.parse("2023-02-19"), obj.adjustInto(LocalDate.parse("2023-02-16")));
	}
	
	@Test
	void displayCurrentDateTimeCanadaTimeZones () {
		//displaying current local date and time for all Canada time zones
		//displaying should contains time zone name
		System.out.println();
		LocalDateTime current = LocalDateTime.now();
		ZoneId.getAvailableZoneIds().stream().filter(zone -> zone.contains("Canada")).forEach(zone -> {
			System.out.println(current.atZone(ZoneId.of(zone)));
		});
	}
	
	@Test
	void getNumberTest() {
		assertEquals(0, getNumber(DayOfWeek.MONDAY, DayOfWeek.MONDAY));
		assertEquals(1, getNumber(DayOfWeek.TUESDAY, DayOfWeek.MONDAY));

		assertEquals(6, getNumber(DayOfWeek.MONDAY, DayOfWeek.SUNDAY));
		assertEquals(0, getNumber(DayOfWeek.TUESDAY, DayOfWeek.SUNDAY));
		assertEquals(5, getNumber(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY));
		
		assertEquals(1, getNumber(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
		assertEquals(2, getNumber(DayOfWeek.TUESDAY, DayOfWeek.TUESDAY));
		assertEquals(3, getNumber(DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY));
		
		
		assertEquals(DayOfWeek.MONDAY, map(DayOfWeek.MONDAY, DayOfWeek.MONDAY));
		assertEquals(DayOfWeek.TUESDAY, map(DayOfWeek.TUESDAY, DayOfWeek.MONDAY));
		
		assertEquals(DayOfWeek.SUNDAY, map(DayOfWeek.MONDAY, DayOfWeek.SUNDAY));
		assertEquals(DayOfWeek.MONDAY, map(DayOfWeek.TUESDAY, DayOfWeek.SUNDAY));
		assertEquals(DayOfWeek.SATURDAY, map(DayOfWeek.SUNDAY, DayOfWeek.SUNDAY));
		
		assertEquals(DayOfWeek.TUESDAY, map(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
		assertEquals(DayOfWeek.WEDNESDAY, map(DayOfWeek.TUESDAY, DayOfWeek.TUESDAY));		
		assertEquals(DayOfWeek.THURSDAY, map(DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY));
	}
	
	private DayOfWeek map(DayOfWeek day, DayOfWeek startDay) {
		return DayOfWeek.of(getNumber(day, startDay) + 1);
	}

	private int getNumber(DayOfWeek day, DayOfWeek startDay) {
//		int diff = startDay.getValue() - DayOfWeek.MONDAY.getValue();
//		int shiftedValue = (day.getValue() + diff - 1) % 7;
		int shiftedValue = (day.getValue() + startDay.getValue() - 2) % 7;
		return shiftedValue;
	}

}
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

}
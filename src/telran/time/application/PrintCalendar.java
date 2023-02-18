package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static final DayOfWeek DEFAULT_FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY; 
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);
	

	public static void main(String[] args)  {
		try {
			int parsed[] = getParsedArguments(args);
			printCalendar(parsed[0], parsed[1], parsed[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(int month, int year, int firstDayOfWeek) {
		int daysShift = getDaysShift(firstDayOfWeek);
		printTitle(month, year);
		printWeekDays(daysShift);
		printDates(month, year, daysShift);
		
	}

	private static int getDaysShift(int firstDayOfWeek) {
		return firstDayOfWeek - 1;
	}

	private static void printDates(int month, int year, int daysShift) {
		int weekDayNumber = getFirstDay(month, year, daysShift);
		int offset = getOffset(weekDayNumber);
		
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		System.out.printf("%s", " ".repeat(offset));
		for(int date = 1; date <= nDays ; date++) {
			System.out.printf("%4d",date);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
	}

	private static int getOffset(int weekDayNumber) {
		
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(int month, int year, int daysShift) {
		
		return LocalDate.of(year, month, 1).getDayOfWeek().minus(daysShift).getValue();
	}

	private static void printWeekDays(int daysShift) {
		System.out.print("  ");
		Arrays.stream(DayOfWeek.values())
		.map(n -> n.plus(daysShift))
		.forEachOrdered(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, locale)));
		System.out.println();
	}

	private static void printTitle(int month, int year) {
		
		System.out.printf(
				"%s%d, %s\n",
				" ".repeat(YEAR_OFFSET),
				year,
				Month.of(month).getDisplayName(TextStyle.FULL,locale)
		);
		
		
	}

	private static int[] getParsedArguments(String[] args) throws Exception {
		
		return args.length == 0 ? getDefaultArgs() : parseArguments(args);
	}

	private static int[] parseArguments(String[] args) throws Exception{
		
		return new int[] {getMonthArgs(args), getYearArgs(args), getDayOfWeekArgs(args)};
	}

	private static int getDayOfWeekArgs(String[] args) throws Exception {
		int res = DEFAULT_FIRST_DAY_OF_WEEK.getValue();
		if (args.length > 2) {
			try {
				res = DayOfWeek.valueOf(args[2].toUpperCase()).getValue();
			} catch (IllegalArgumentException e) {
				throw new Exception("first day of week is incorrect");
			}
		}
		return res;
	}

	private static int getYearArgs(String[] args) throws Exception{
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception{
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
		
		
	}

	private static int[] getDefaultArgs() {
		LocalDate current = LocalDate.now();
		return new int[] {current.getMonth().getValue(), current.getYear(), DEFAULT_FIRST_DAY_OF_WEEK.getValue()};
	}

}
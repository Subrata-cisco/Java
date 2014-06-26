package com.java8.date;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * The java.time.zone package provides support for time zones, their rules and the resulting gaps and overlaps 
 * in the local time-line typically caused by Daylight Saving Time. There is also the java.time.format package 
 * for printing and parsing date-time objects, although in most cases, the date and time classes' toString() 
 * and parse() methods should be sufficient. The java.time.temporal package provides access to date and time 
 * using fields and units, additional value type classes for the most important sub-parts of a date, and base 
 * support for calendar systems other than the default ISO. This package provides additional functionality for 
 * more advanced use cases.
 * 
 * @author Subrata Saha.
 *
 */
public interface NewDateAPIExample {
	public static void main(String[] args) {
		
		//example1();
		//example2();
		//example3();
		//example4();
		//example5();
		//example6();
		//example7();
		//example8();
		//example9();
		//example10();
		//example11();
		//example12();
	}

	public static void example12() {
		// LocalDate/LocalTime <-> LocalDateTime
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalDateTime dateTimeFromDateAndTime = LocalDateTime.of(date, time);
		LocalDate dateFromDateTime = LocalDateTime.now().toLocalDate();
		LocalTime timeFromDateTime = LocalDateTime.now().toLocalTime();
		 
		// Instant <-> LocalDateTime
		Instant instant = Instant.now();
		LocalDateTime dateTimeFromInstant = LocalDateTime.ofInstant(instant, ZoneId.of("America/Los_Angeles"));
		Instant instantFromDateTime = LocalDateTime.now().toInstant(ZoneOffset.ofHours(-2));
		 
		// convert old date/calendar/timezone classes
		Instant instantFromDate = new Date().toInstant();
		Instant instantFromCalendar = Calendar.getInstance().toInstant();
		ZoneId zoneId = TimeZone.getDefault().toZoneId();
		ZonedDateTime zonedDateTimeFromGregorianCalendar = new GregorianCalendar().toZonedDateTime();
		 
		// convert to old classes
		Date dateFromInstant = Date.from(Instant.now());
		TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of("America/Los_Angeles"));
		GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.now());
	}

	public static void example11() {
		// 2014-04-01 10:45
		LocalDateTime dateTime = LocalDateTime.of(2014, Month.APRIL, 1, 10, 45);
		 
		// format as basic ISO date format (20140220)
		String asBasicIsoDate = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
		 
		// format as ISO week date (2014-W08-4)
		String asIsoWeekDate = dateTime.format(DateTimeFormatter.ISO_WEEK_DATE);
		 
		// format ISO date time (2014-02-20T20:04:05.867)
		String asIsoDateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
		 
		// using a custom pattern (01/04/2014)
		String asCustomPattern = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		 
		// french date formatting (1. avril 2014)
		String frenchDate = dateTime.format(DateTimeFormatter.ofPattern("d. MMMM yyyy", new Locale("fr")));
		 
		// using short german date/time formatting (01.04.14 10:45)
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
		    .withLocale(new Locale("de"));
		String germanDateTime = dateTime.format(formatter);
		 
		// parsing date strings
		LocalDate fromIsoDate = LocalDate.parse("2014-01-20");
		LocalDate fromIsoWeekDate = LocalDate.parse("2014-W14-2", DateTimeFormatter.ISO_WEEK_DATE);
		LocalDate fromCustomPattern = LocalDate.parse("20.01.2014", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	public static void example10() {
		LocalDate firstDate = LocalDate.of(2010, 5, 17); // 2010-05-17
		LocalDate secondDate = LocalDate.of(2015, 3, 7); // 2015-03-07
		Period period = Period.between(firstDate, secondDate);
		 
		int days = period.getDays(); // 18
		int months = period.getMonths(); // 9
		int years = period.getYears(); // 4
		boolean isNegative = period.isNegative(); // false
		 
		Period twoMonthsAndFiveDays = Period.ofMonths(2).plusDays(5);
		LocalDate sixthOfJanuary = LocalDate.of(2014, 1, 6);
		 
		// add two months and five days to 2014-01-06, result is 2014-03-11
		LocalDate eleventhOfMarch = sixthOfJanuary.plus(twoMonthsAndFiveDays);
		 
		 
		// durations
		 
		Instant firstInstant= Instant.ofEpochSecond( 1294881180 ); // 2011-01-13 01:13
		Instant secondInstant = Instant.ofEpochSecond(1294708260); // 2011-01-11 01:11
		 
		Duration between = Duration.between(firstInstant, secondInstant);
		 
		// negative because firstInstant is after secondInstant (-172920)
		long seconds = between.getSeconds();
		 
		// get absolute result in minutes (2882)
		long absoluteResult = between.abs().toMinutes();
		 
		// two hours in seconds (7200)
		long twoHoursInSeconds = Duration.ofHours(2).getSeconds();
	}

	public static void example9() {
		// current time
		Instant now = Instant.now();
		 
		// from unix timestamp, 2010-01-01 12:00:00
		Instant fromUnixTimestamp = Instant.ofEpochSecond(1262347200);
		 
		// same time in millis
		Instant fromEpochMilli = Instant.ofEpochMilli(1262347200000l);
		 
		// parsing from ISO 8601
		Instant fromIso8601 = Instant.parse("2010-01-01T12:00:00Z");
		 
		// toString() returns ISO 8601 format, e.g. 2014-02-15T01:02:03Z
		String toIso8601 = now.toString();
		 
		// as unix timestamp
		long toUnixTimestamp = now.getEpochSecond();
		 
		// in millis
		long toEpochMillis = now.toEpochMilli();
		 
		// plus/minus methods are available too
		Instant nowPlusTenSeconds = now.plusSeconds(10);
	}

	public static void example8() {
		ZoneId losAngeles = ZoneId.of("America/Los_Angeles");
		ZoneId berlin = ZoneId.of("Europe/Berlin");
		
		// 2014-02-20 12:00
		LocalDateTime dateTime = LocalDateTime.of(2014, 02, 20, 12, 0);
		
		// 2014-02-20 12:00, Europe/Berlin (+01:00)
		ZonedDateTime berlinDateTime = ZonedDateTime.of(dateTime, berlin);
		 
		// 2014-02-20 03:00, America/Los_Angeles (-08:00)
		ZonedDateTime losAngelesDateTime = berlinDateTime.withZoneSameInstant(losAngeles);
		 
		int offsetInSeconds = losAngelesDateTime.getOffset().getTotalSeconds(); // -28800
		
		// a collection of all available zones
		Set<String> allZoneIds = ZoneId.getAvailableZoneIds();
		 
		// using offsets
		LocalDateTime date = LocalDateTime.of(2013, Month.JULY, 20, 3, 30);
		ZoneOffset offset = ZoneOffset.of("+05:00");
		 
		// 2013-07-20 03:30 +05:00
		OffsetDateTime plusFive = OffsetDateTime.of(date, offset);
		
		// 2013-07-19 20:30 -02:00
		OffsetDateTime minusTwo = plusFive.withOffsetSameInstant(ZoneOffset.ofHours(-2));
	}

	public static void example7() {
		LocalDate date = LocalDate.of(2014, Month.FEBRUARY, 25); // 2014-02-25
		// first day of february 2014 (2014-02-01)
		LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
		// last day of february 2014 (2014-02-28)
		LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		System.out
				.println("****** Subrata -> [TemporalAdjusters] 2014-02-25 date ::"
						+ date
						+ " firstDayOfMonth ::"
						+ firstDayOfMonth
						+ " lastDayOfMonth ::" + lastDayOfMonth);
	}

	public static void example6() {
		//information about the month
		LocalDate date = LocalDate.of(2014, 2, 15); 
		Month february = date.getMonth(); // FEBRUARY
		int februaryIntValue = february.getValue(); // 2
		int minLength = february.minLength(); // 28
		int maxLength = february.maxLength(); // 29
		Month firstMonthOfQuarter = february.firstMonthOfQuarter();//JANUARY
		System.out.println("****** Subrata -> create date with 2014, 2, 15 ::"+date+" month ::"+february+" month int val ::"+februaryIntValue
				+" Month minLength ::"+minLength+" Month maxLength ::"+maxLength+" firstMonthOfQuarter ::"+firstMonthOfQuarter);

		//information about the year
		int year = date.getYear(); //2014
		int dayOfYear = date.getDayOfYear(); // 46
		int lengthOfYear = date.lengthOfYear(); // 365
		boolean isLeapYear = date.isLeapYear();//false
		System.out.println("****** Subrata -> year ::"+year+" dayOfYear ::"+dayOfYear+" lengthOfYear ::"+lengthOfYear+" isLeapYear ::"+isLeapYear);

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		int dayOfWeekIntValue = dayOfWeek.getValue(); //6
		String dayOfWeekName = dayOfWeek.name(); //SATURDAY
		System.out.println("****** Subrata -> dayOfWeek ::"+dayOfWeek+" dayOfWeekIntValue ::"+dayOfWeekIntValue+" dayOfWeekName ::"+dayOfWeekName);

		int dayOfMonth = date.getDayOfMonth(); // 15
		LocalDateTime startOfDay = date.atStartOfDay(); // 2014-02-15 00:00
		System.out.println("****** Subrata -> dayOfMonth ::"+dayOfMonth+" startOfDay ::"+startOfDay);
		 
		// time information
		LocalTime time = LocalTime.of(15,30); // 15:30:00
		int hour = time.getHour(); // 15
		int second = time.getSecond(); // 0
		int minute = time.getMinute(); // 30
		int secondOfDay = time.toSecondOfDay(); // 55800
		System.out.println("****** Subrata -> hour ::"+hour+" second ::"+second+" minute ::"+minute+" secondOfDay ::"+secondOfDay);
	}

	public static void example5() {
		Clock clock = Clock.systemDefaultZone(); 
		LocalTime time = LocalTime.now(clock);
		
		LocalTime now = LocalTime.now(Clock.systemUTC());
		LocalTime currentTimeInLosAngeles = LocalTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println("****** Subrata -> time using Clock ::"+time+" UTC time ::"+now+" currentTimeInLosAngeles ::"+currentTimeInLosAngeles);
	}

	public static void example4() {
		LocalDate today = LocalDate.now();
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
		
		System.out.println("LocalDate :: example Today ::"+today+" nextWeek ::"+nextWeek+" nextMonth ::"+nextMonth+" nextYear ::"+nextYear+" nextDecade ::"+nextDecade);
	}

	public static void example3() {
		LocalDate date = LocalDate.of(2014, 3, 15);
		date = LocalDate.of(2014, Month.MARCH, 15);
		LocalTime time = LocalTime.of(12, 15, 0);
		LocalDateTime datetime = date.atTime(time);
		
		System.out.println("LocalDate :: Date ->"+date+" LocalTime ::  time ->"+time +" LocalDateTime :: datetime ->"+datetime);
	}

	public static void example2() {
		LocalDate today = LocalDate.now();
	    LocalDate thirtyDaysFromNow = today.plusDays(30);
		LocalDate nextMonth = today.plusMonths(1);
		LocalDate aMonthAgo = today.minusMonths(1);
		
		System.out.println("LocalDate (plus*** API ):: Today ->"+today+" ThirtyDaysFromNow ->"+thirtyDaysFromNow+" nextMonth ->"+nextMonth+" aMonthAgo ->"+aMonthAgo);
	}

	public static void example1() {
		LocalTime now = LocalTime.now();
		LocalTime later = now.plus(8,ChronoUnit.HOURS);
		System.out.println("LocalTime :: Now ->"+now+" and 8 hours later ->"+later);
		// 12345th second of day (03:25:45)
		LocalTime fromSecondsOfDay = LocalTime.ofSecondOfDay(12345);
		 
		// 2014-10-02 12:30
		LocalDateTime secondAug2014 = LocalDateTime.of(2014, 10, 2, 12, 30);
		 
		// 2014-12-24 12:00
		LocalDateTime christmas2014 = LocalDateTime.of(2014, Month.DECEMBER, 24, 12, 0);
		
		System.out.println("LocalTime :: fromSecondsOfDay ->"+fromSecondsOfDay+" secondAug2014 ->"+secondAug2014+" christmas2014 ::"+christmas2014);
		
		LocalDate date = LocalDate.of(2014, 2, 15); // 2014-02-15
		boolean isBefore = LocalDate.now().isBefore(date); // false
		
		System.out.println("****** Subrata -> 2014-02-15 is before now ->"+isBefore);
	}
}

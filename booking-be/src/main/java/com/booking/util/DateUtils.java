package com.booking.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.util.StringUtils;

import com.booking.exception.BadRequestException;

public class DateUtils {

	private DateUtils() {
	}

	public static LocalDateTime increaseDateTime(LocalDateTime date, Integer daysToIncrease, Integer monthsToIncrease,
			Integer yearsToIncrease, boolean ceil) {
		if (daysToIncrease != null) {
			date = date.plusDays(daysToIncrease);
		}
		if (monthsToIncrease != null) {
			date = date.plusMonths(monthsToIncrease);
		}
		if (yearsToIncrease != null) {
			date = date.plusYears(yearsToIncrease);
		}
		if (ceil) {
			date = date.with(LocalTime.MAX);
		}
		return date;
	}

	public static LocalDateTime increaseTime(LocalDateTime date, Integer hoursToIncrease, Integer minutesToIncrease) {
		if (hoursToIncrease != null) {
			date = date.plusHours(hoursToIncrease);
		}
		if (minutesToIncrease != null) {
			date = date.plusMinutes(minutesToIncrease);
		}
		return date;
	}

	public static String convertDateToStr(LocalDate date) {
		String dateStr = null;
		if (date != null) {
			DateFormat outputFormat = new SimpleDateFormat(MyConstants.PATTERN_TIME_WITH_MEREDIEM);
			dateStr = outputFormat.format(date);

		}
		return dateStr;
	}

	public static LocalDateTime convertStrToDateTime(String dateTimeStr) throws DateTimeParseException {
		LocalDateTime formattedDate = null;
		if (StringUtils.hasText(dateTimeStr)) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MyConstants.PATTERN_TIME_WITH_MEREDIEM);
				formattedDate = LocalDateTime.parse(dateTimeStr, formatter);
			} catch (Exception e) {
				throw new BadRequestException("Invalid date time format");
			}
		}
		return formattedDate;
	}

	public static LocalTime convertStrToTime(String timeStr) {
		LocalTime formattedTime = null;
		if (StringUtils.hasText(timeStr)) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MyConstants.PATTERN_TIME_WITH_MEREDIEM);
				formattedTime = LocalTime.parse(timeStr, formatter);
			} catch (Exception e) {
				throw new BadRequestException("Invalid time format");
			}
		}
		return formattedTime;
	}

	public static String convertDateTimeToStr(LocalDateTime dateTime) {
		String dateStr = null;
		if (dateTime != null) {
			try {
				DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(MyConstants.PATTERN_TIME_WITH_MEREDIEM);
				dateStr = outputFormat.format(dateTime);
			} catch (DateTimeParseException e) {
				throw new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "Date");
			}
		}
		return dateStr;
	}

	public static Long getTimeDifferenceInDays(LocalDate startDate, LocalDate endDate) {
		return Duration.between(startDate, endDate).toDays();
	}

	public static Long getTimeDifferenceInDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return Duration.between(startDateTime, endDateTime).toDays();
	}

//	public static String convertDateTimeToStrWithMeridiem(LocalDateTime executionDateTime) {
//		String timeWithMerediem = null;
//		try {
//			DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern(MyConstants.PATTERN_TIME_WITH_MEREDIEM);
//			timeWithMerediem = outputFormat.format(executionDateTime);
//		} catch (DateTimeParseException e) {
//			throw new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "Date");
//		}
//		return timeWithMerediem;
//	}

	public static LocalDateTime ceilDate(LocalDateTime now) {
		LocalDate localDate = now.toLocalDate();
		return localDate.atTime(LocalTime.MAX);
	}

	public static LocalDateTime floorDate(LocalDateTime now) {
		LocalDate localDate = now.toLocalDate();
		return localDate.atTime(LocalTime.MIN);
	}

}

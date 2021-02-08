package com.app.handyman.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;

public class DateTimeUltils {

	public static  boolean isDateValid(@NonNull String startDate, @NonNull String endDate) {
		return formatDate(endDate).isBefore(formatDate(startDate))
				|| formatDate(endDate).isEqual(formatDate(startDate));
	}

	public static  LocalDateTime formatDate(@NonNull String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		return LocalDateTime.parse(date, format);
	}

}

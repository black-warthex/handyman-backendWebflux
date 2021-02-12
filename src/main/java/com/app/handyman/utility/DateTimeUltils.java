package com.app.handyman.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.entity.ServiceReportEntity;

import lombok.NonNull;
import reactor.core.publisher.Flux;

public class DateTimeUltils {
	 private DateTimeUltils() {
		    throw new IllegalStateException("Utility class");
		  }
	private static final String SUNDAY = "SUNDAY";

	public static boolean isDateInvalid(@NonNull String startDate, @NonNull String endDate) {
		return formatDate(endDate).isBefore(formatDate(startDate))
				|| formatDate(endDate).isEqual(formatDate(startDate));
	}

	private static LocalDateTime formatDate(@NonNull String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		return LocalDateTime.parse(date, format);
	}

	public static Double getNormalHours(@NonNull Flux<ServiceReportEntity> serviceReport) throws InterruptedException {

		Double empty = (double) 0;
		HoursReportDto report = new HoursReportDto(empty, empty, empty, empty, empty, empty);

		serviceReport.filter(data -> !getDay(data.getStartDate()).equals(SUNDAY))
				.filter(data -> getHour(data.getStartDate()) >= 7).filter(data -> getHour(data.getStartDate()) < 20)
				.collect(Collectors.summingDouble(data -> getTimeDifference(data.getStartDate(), data.getEndDate())))
				.subscribe(x -> report.setNormalHours(Double.parseDouble(x.toString())));
		Thread.sleep(100);

		return report.getNormalHours();
	}

	public static Double getNightHours(@NonNull Flux<ServiceReportEntity> serviceReport) throws InterruptedException {

		Double empty = (double) 0;
		HoursReportDto report = new HoursReportDto(empty, empty, empty, empty, empty, empty);

		serviceReport.filter(data -> !getDay(data.getStartDate()).equals(SUNDAY))
				.filter(data -> getHour(data.getStartDate()) >= 20)
				.collect(Collectors.summingDouble(data -> getTimeDifference(data.getStartDate(), data.getEndDate())))
				.subscribe(x -> report.setNightHours(Double.parseDouble(x.toString())));
		Thread.sleep(100);

		return report.getNightHours();
	}

	public static Double getSundayHours(@NonNull Flux<ServiceReportEntity> serviceReport) throws InterruptedException {

		Double empty = (double) 0;
		HoursReportDto report = new HoursReportDto(empty, empty, empty, empty, empty, empty);

		serviceReport.filter(data -> getDay(data.getStartDate()).equals(SUNDAY))
				.collect(Collectors.summingDouble(data -> getTimeDifference(data.getStartDate(), data.getEndDate())))
				.subscribe(x -> report.setSundayHours(Double.parseDouble(x.toString())));
		Thread.sleep(100);

		return report.getSundayHours();
	}

	public static String getDay(@NonNull String date) {
		return formatDate(date).getDayOfWeek().toString();
	}

	public static Double getHour(@NonNull String date) {
		return (double) formatDate(date).getHour();
	}

	private static Double getTimeDifference(@NonNull String startDate, @NonNull String endDate) {
		return (double) (ChronoUnit.MINUTES.between(formatDate(startDate), formatDate(endDate))) / 60;
	}

}

package com.app.handyman.utility;



import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.entity.ServiceReportEntity;

import lombok.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CalculateHours {

	private static HoursReportDto report = new HoursReportDto(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	private static final Integer WEEKLYHOURS = 48;
	
	
	public static Mono<HoursReportDto>getReportHours(@NonNull Flux<ServiceReportEntity> serviceReport) throws InterruptedException{
		
		report.setNormalHours(0.0);
		report.setSundayHours(0.0);
		report.setNightHours(0.0);
		
		report.setNormalExtraHours(0.0);		
		report.setNightlyOvertime(0.0);
		report.setSundayExtraHours(0.0);
		
		getNormalAndExtraHours(DateTimeUltils.getNormalHours(serviceReport));
		getNightAndExtraHours(DateTimeUltils.getNightHours(serviceReport));
		getSundayAndExtraHours(DateTimeUltils.getSundayHours(serviceReport));
		
		return Mono.just(report);
	}
	

	private static void getNormalAndExtraHours(@NonNull Double normalHours) {

		report.setNormalHours(normalHours);
		
		if (normalHours > WEEKLYHOURS) {
			report.setNormalHours((double) WEEKLYHOURS);
			report.setNormalExtraHours(normalHours - WEEKLYHOURS);
		}

	}

	private static void getNightAndExtraHours(@NonNull Double nightHours) {
		if (report.getNormalHours() > WEEKLYHOURS) {
			report.setNightlyOvertime(nightHours);
		}

		if ((report.getNormalHours() + nightHours) < WEEKLYHOURS) {
			report.setNightHours(nightHours);
		} else {
			report.setNightHours(WEEKLYHOURS - report.getNormalHours());
			report.setNightlyOvertime(nightHours - report.getNightHours());
		}
	}

	private static void getSundayAndExtraHours(@NonNull Double sundayHours) {
		if (report.getNormalHours()+report.getNightHours() < WEEKLYHOURS) {
			report.setSundayHours(sundayHours);
		} else {
			report.setSundayExtraHours(sundayHours);
		}
	}

}

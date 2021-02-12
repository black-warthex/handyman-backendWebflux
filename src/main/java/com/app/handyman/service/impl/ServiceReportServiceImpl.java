package com.app.handyman.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.dto.ResponseDto;
import com.app.handyman.entity.ServiceReportEntity;
import com.app.handyman.repository.IServiceProductRepository;
import com.app.handyman.service.IServiceReportService;
import com.app.handyman.utility.CalculateHours;
import com.app.handyman.utility.DateTimeUltils;
import com.app.handyman.utility.DefaultAnswerEnum;


import reactor.core.publisher.Mono;

@Service
public class ServiceReportServiceImpl implements IServiceReportService {

	@Autowired
	private IServiceProductRepository repository;

	@Override
	public Mono<ResponseDto> addServiceReport(ServiceReportEntity report) {

		if (DateTimeUltils.isDateInvalid(report.getStartDate(), report.getEndDate())) {
			return Mono.just(new ResponseDto(false, DefaultAnswerEnum.MESSAGE_DATE_ERROR.getValue()));
		}

		return repository.findByTechnicalIdAndServiceId(report.getTechnicalId(), report.getServiceId())
				.flatMap(x -> Mono.just(new ResponseDto(false, DefaultAnswerEnum.MESSAGE_DUPLICATE.getValue())))
				.switchIfEmpty(repository.save(report)
						.then(Mono.just(new ResponseDto(true, DefaultAnswerEnum.MESSAGE_INSERT.getValue()))));
	}

	@Override
	public Mono<HoursReportDto> calculateHours(String technicalId, String weekNumber) throws InterruptedException {
		
		return CalculateHours.getReportHours(repository.findByTechnicalIdAndWeekNumber(technicalId, weekNumber));

	}

}

package com.app.handyman.service;

import org.springframework.stereotype.Service;

import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.dto.ResponseDto;
import com.app.handyman.entity.ServiceReportEntity;

import reactor.core.publisher.Mono;

@Service
public interface IServiceReportService {
	
	public Mono<ResponseDto> addServiceReport(ServiceReportEntity report);
	public Mono<HoursReportDto> calculateHours (String technicalId,String weekNumber) throws InterruptedException;

}

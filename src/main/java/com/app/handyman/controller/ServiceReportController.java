package com.app.handyman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.dto.ResponseDto;
import com.app.handyman.dto.ServiceReportDto;
import com.app.handyman.service.IServiceReportService;
import com.app.handyman.utility.DtoToEntity;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/report")
public class ServiceReportController {

	@Autowired
	private IServiceReportService service;
	private DtoToEntity change = new DtoToEntity();

	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Mono<ResponseDto> addReport(@RequestBody ServiceReportDto	report) {	
		return (service.addServiceReport(change.reportToEntity(report)));
	}
	
	@GetMapping(path = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Mono<HoursReportDto> getHoursReport(
			@RequestParam(name = "technicalId") String technicalId,
			@RequestParam(name = "weekNumber") String weekNumber) throws InterruptedException {
		return service.calculateHours(technicalId, weekNumber);
		
	}

}

package com.app.handyman;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.app.handyman.dto.ResponseDto;
import com.app.handyman.entity.ServiceReportEntity;
import com.app.handyman.service.IServiceReportService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
class HandymanApplicationTests {

	@MockBean
	IServiceReportService repository;

	@Autowired
	private ApplicationContext context;

	private WebTestClient webClient;

	@BeforeEach
	void beforeach() {
		this.webClient = (WebTestClient) WebTestClient.bindToApplicationContext(context).configureClient()
				.baseUrl("/report/").build();
	}

	@Test
	void testAddReport() {
		ServiceReportEntity report = new ServiceReportEntity();
		ResponseDto response = new ResponseDto(true, "ok");

		report.setTechnicalId("wtx-1");
		report.setServiceId("s-99");
		report.setStartDate("2021-01-18T07:00");
		report.setEndDate("2021-01-20T07:15");
		report.setWeekNumber("4");

		Mockito.when(repository.addServiceReport(report)).thenReturn(Mono.just(response));

		webClient.post().uri("add").contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(response), ResponseDto.class).exchange().expectStatus().isOk();

		Mockito.verify(repository, times(0)).addServiceReport(report);
	}
	@Test
	void testGetReport() {
		ServiceReportEntity report = new ServiceReportEntity();
		ResponseDto response = new ResponseDto(true, "ok");
		
		String technicalId ="wtx-1";
		String weekNumber ="4";
		
		report.setTechnicalId("wtx-1");
		report.setServiceId("s-99");
		report.setStartDate("2021-01-18T07:00");
		report.setEndDate("2021-01-20T07:15");
		report.setWeekNumber("4");

		Mockito.when(repository.addServiceReport(report)).thenReturn(Mono.just(response));

		webClient.get().uri("add").contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(response), ResponseDto.class).exchange().expectStatus().isOk();

		Mockito.verify(repository, times(0)).addServiceReport(report);
	}

}
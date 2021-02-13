package com.app.handyman;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.reactive.server.WebTestClient;

import com.app.handyman.dto.HoursReportDto;
import com.app.handyman.dto.ResponseDto;
import com.app.handyman.entity.ServiceReportEntity;
import com.app.handyman.service.IServiceReportService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
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

		webClient.post().uri("add").contentType(MediaType.APPLICATION_JSON).body(Mono.just(response), ResponseDto.class)
				.exchange().expectStatus().isOk();

		Mockito.verify(repository, times(0)).addServiceReport(report);
	}

	@Test
	void testGetReport() throws InterruptedException {
		HoursReportDto report = new HoursReportDto();

		String technicalId = "wtx-1";
		String weekNumber = "4";

		report.setNormalHours(1.3);
		report.setNormalExtraHours(3.4);

		report.setNightHours(4.1);
		report.setNightlyOvertime(3.1);

		report.setSundayHours(0.0);
		report.setSundayExtraHours(7.0);

		Mockito.when(repository.calculateHours(technicalId, weekNumber)).thenReturn(Mono.just(report));

		webClient.get()
				.uri(uriBuilder -> uriBuilder.path("report").queryParam("technicalId", technicalId)
						.queryParam("weekNumber", weekNumber).build())
				.exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
				.expectBody(HoursReportDto.class).isEqualTo(report);

	}

}
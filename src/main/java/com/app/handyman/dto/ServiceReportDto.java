package com.app.handyman.dto;

import lombok.Data;

@Data
public class ServiceReportDto {
	private String serviceId;
	private String technicalId;
	private String startDate;
	private String endDate;
	private String weekNumber;
}

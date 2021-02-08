package com.app.handyman.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "service_report")
@Data @AllArgsConstructor @NoArgsConstructor
public class ServiceReportEntity {
	@Id
	private String serviceId;
	private String technicalId;
	private String startDate;
	private String endDate;
	
}

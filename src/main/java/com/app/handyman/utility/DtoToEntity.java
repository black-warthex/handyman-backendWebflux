package com.app.handyman.utility;

import com.app.handyman.dto.ServiceReportDto;
import com.app.handyman.entity.ServiceReportEntity;

import lombok.NonNull;

public class DtoToEntity {
	
	public ServiceReportEntity reportToEntity(@NonNull ServiceReportDto report) {

		ServiceReportEntity reportEntity = new ServiceReportEntity();

		reportEntity.setTechnicalId(report.getTechnicalId());
		reportEntity.setServiceId(report.getServiceId());
		reportEntity.setStartDate(report.getStartDate());
		reportEntity.setEndDate(report.getEndDate());

		return reportEntity;

	}
}

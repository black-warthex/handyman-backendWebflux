package com.app.handyman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoursReportDto {

	private Double normalHours;
	private Double nightHours;
	private Double sundayHours;
	private Double normalExtraHours;
	private Double nightlyOvertime;
	private Double sundayExtraHours;

}

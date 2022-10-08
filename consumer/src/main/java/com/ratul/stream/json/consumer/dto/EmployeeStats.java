package com.ratul.stream.json.consumer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeStats {
	
	private Long totalEmployeeCount;
	private Long femaleEmployeeCount = 0L;
	private Long maleEmployeeCount = 0L;
	private Long reportGenerationDelayInSeconds;

	public void incrementFemaleEmployeeCount() {
		femaleEmployeeCount++;
	}

	public void incrementMaleEmployeeCount() {
		maleEmployeeCount++;
	}
}

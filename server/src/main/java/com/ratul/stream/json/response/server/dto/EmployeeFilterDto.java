package com.ratul.stream.json.response.server.dto;

import com.ratul.stream.json.response.server.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeFilterDto {
	
	private Long offset;

	private Gender gender;
	
	private String firstNameLike;
}

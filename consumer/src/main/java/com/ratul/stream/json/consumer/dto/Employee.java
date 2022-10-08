package com.ratul.stream.json.consumer.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ratul.stream.json.consumer.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"empNo", "birthDate", "firstName", "lastName", "gender", "hireDate"})
public class Employee {
	
	private Long empNo;

	private Date birthDate;

	private String firstName;

	private String lastName;

	private Gender gender;

	private Date hireDate;
}
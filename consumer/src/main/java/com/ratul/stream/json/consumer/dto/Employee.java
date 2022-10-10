package com.ratul.stream.json.consumer.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.ratul.stream.json.consumer.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonPropertyOrder({ "Employee No", "First Name", "Last Name", "Gender", "Birth Date", "Hire Date" })
public class Employee {

	@Getter(onMethod = @__(@JsonGetter("Employee No")))
	@Setter(onMethod = @__(@JsonSetter("empNo")))
	private Long empNo;

	@Getter(onMethod = @__(@JsonGetter("Birth Date")))
	@Setter(onMethod = @__(@JsonSetter("birthDate")))
	private Date birthDate;

	@Getter(onMethod = @__(@JsonGetter("First Name")))
	@Setter(onMethod = @__(@JsonSetter("firstName")))
	private String firstName;

	@Getter(onMethod = @__(@JsonGetter("Last Name")))
	@Setter(onMethod = @__(@JsonSetter("lastName")))
	private String lastName;

	@Getter(onMethod = @__(@JsonGetter("Gender")))
	@Setter(onMethod = @__(@JsonSetter("gender")))
	private Gender gender;

	@Getter(onMethod = @__(@JsonGetter("Hire Date")))
	@Setter(onMethod = @__(@JsonSetter("hireDate")))
	private Date hireDate;
}
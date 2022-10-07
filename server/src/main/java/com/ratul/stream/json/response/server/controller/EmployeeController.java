package com.ratul.stream.json.response.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ratul.stream.json.response.server.entity.Employee;
import com.ratul.stream.json.response.server.service.EmployeeService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;

	@GetMapping(value = "/employees", params = {"at-once"})
	public List<Employee> allEmployeeAtOnce() {
		return employeeService.findAllAtOnce();
	}

	@GetMapping(value = "/employees", params = "stream")
	public void streamAllEmployee(HttpServletResponse response) throws IOException {
		response.setHeader(HttpHeaders.CONTENT_ENCODING, MediaType.APPLICATION_JSON_VALUE);
		employeeService.findAllStreaming(response.getOutputStream());
	}
}



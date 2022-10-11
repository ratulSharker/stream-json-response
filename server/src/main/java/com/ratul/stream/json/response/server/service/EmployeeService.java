package com.ratul.stream.json.response.server.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratul.stream.json.response.server.dto.EmployeeFilterDto;
import com.ratul.stream.json.response.server.entity.Employee;
import com.ratul.stream.json.response.server.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private ObjectMapper objectMapper;


	@PostConstruct
	public void postConstruct() {
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd MMMM YYYY"));
	}

	public List<Employee> findAllAtOnce() {
		return employeeRepository.findAll();
	}

	public void findAllStreaming(EmployeeFilterDto  employeeFilterDto, OutputStream outputStream) throws IOException {
		Stream<Employee> employees = employeeRepository.streamAll();

		JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream);

		try {

			jsonGenerator.writeStartObject();
			jsonGenerator.writeArrayFieldStart("employees");

			Iterator<Employee> employeeIterator = employees.iterator();
			while(employeeIterator.hasNext()) {
				Employee employee = employeeIterator.next();
				employeeRepository.detach(employee);
				jsonGenerator.writeObject(employee);
			}
	
			jsonGenerator.writeEndArray();
			jsonGenerator.writeEndObject();		

		} catch(Exception ex) {
			throw ex;
		} finally {
			
			if(employees != null) {
				employees.close();
			}

			if(jsonGenerator != null)  {
				jsonGenerator.close();
			}
		}
	}
}

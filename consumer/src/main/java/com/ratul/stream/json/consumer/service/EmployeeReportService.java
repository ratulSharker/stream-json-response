package com.ratul.stream.json.consumer.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.ratul.stream.json.consumer.dto.Employee;
import com.ratul.stream.json.consumer.dto.EmployeeStats;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeReportService {
	
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final CsvMapper csvMapper;

	public EmployeeReportService(RestTemplate restTemplate, ObjectMapper objectMapper,
			@Qualifier("csv-mapper") CsvMapper csvMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.csvMapper = csvMapper;
	}

	public Long fetchAllEmployee(Consumer<Employee> employeeConsumer) {

		String url = "http://localhost:8080/employees?stream=true";

		return restTemplate.execute(url, HttpMethod.GET, null, (response) -> {

			Long employeeCount = 0L;
			
			JsonParser jsonParser = objectMapper.getFactory().createParser(response.getBody());

			if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
				if(jsonParser.nextFieldName() == "employees") {
					if(jsonParser.nextToken() == JsonToken.START_ARRAY) {
						while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
							Employee employee = jsonParser.readValueAs(Employee.class);
							employeeConsumer.accept(employee);
							employeeCount++;
						}
						// TODO: Check what if object end is not consumed ?
					}
				}
			}

			jsonParser.close();

			return employeeCount;
		});

	}

	public EmployeeStats prepareEmployeeStats() {
		final Long startTime = System.currentTimeMillis();
		final EmployeeStats employeeStats = new EmployeeStats();

		final Long totalEmployeeCount = fetchAllEmployee((employee) -> {
			switch (employee.getGender()) {
				case F:
					employeeStats.incrementFemaleEmployeeCount();
					break;
				case M:
					employeeStats.incrementMaleEmployeeCount();
					break;
			}
		});

		final Long endTime = System.currentTimeMillis();

		employeeStats.setReportGenerationDelayInSeconds((endTime - startTime) / 1000);
		employeeStats.setTotalEmployeeCount(totalEmployeeCount);

		return employeeStats;
	}

	public File prepareEmployeeReportFile() throws IOException {

		CsvSchema schema = csvMapper.schemaFor(Employee.class).withHeader();

		File tempFile = null;
		FileOutputStream fos = null;

		try {

			tempFile = File.createTempFile("employee", "csv");
			fos = new FileOutputStream(tempFile);
			CsvGenerator csvGenerator = csvMapper.getFactory().createGenerator(fos);
			csvGenerator.setSchema(schema);

			fetchAllEmployee((employee) -> {
				try {
					csvGenerator.writeObject(employee);
				} catch(IOException ex) {
					throw new RuntimeException(ex);
				}
			});
			

		} catch(IOException ex) {
			if(fos != null) {
				fos.close();
			}
			if(tempFile != null) {
				tempFile.delete();
			}
		} finally {
			if(fos != null) {
				fos.close();
			}
		}
		return tempFile;
	}

}

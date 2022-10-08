package com.ratul.stream.json.consumer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.ratul.stream.json.consumer.dto.EmployeeStats;
import com.ratul.stream.json.consumer.service.EmployeeReportService;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeReportService employeeReportService;

	@GetMapping("/employee-stats")
	public String getEmployeeStats(Model model) {
		EmployeeStats employeeStats = employeeReportService.prepareEmployeeStats();
		model.addAttribute("stat", employeeStats);
		return "employee-stats";
	}

	@GetMapping("/employee-report")
	public String getReportDownloadPage() {
		return "employee-report";
	}

	@GetMapping("/download-employee-report")
	public void downloadEmployeeReport(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=employees.csv");

		File csvTempFile = employeeReportService.prepareEmployeeReportFile();
		FileInputStream fis = new FileInputStream(csvTempFile);

		IOUtils.copy(fis, response.getOutputStream());

		fis.close();

		//TODO: Need to be more careful about deleting this file.
		csvTempFile.delete();
	}
}

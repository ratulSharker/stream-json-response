package com.ratul.stream.json.consumer.config;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		for(HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
			if(converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
				ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
				objectMapper.setDateFormat(new SimpleDateFormat("dd MMMM YYYY"));
			}
		}

		return restTemplate;
	}

	@Bean
	@Primary
	public ObjectMapper restTemplateObjectMapper(RestTemplate restTemplate) {
		for(HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
			if(converter instanceof MappingJackson2HttpMessageConverter) {
				return ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
			}
		}

		return null;
	}

	@Bean(name = "csv-mapper")
	public CsvMapper csvMapper() {
		CsvMapper csvMapper = new CsvMapper();
		csvMapper.setDateFormat(new SimpleDateFormat("dd MMMM YYYY"));
		return csvMapper;
	}

}

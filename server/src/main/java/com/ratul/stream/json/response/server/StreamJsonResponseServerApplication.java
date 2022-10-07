package com.ratul.stream.json.response.server;

import com.ratul.stream.json.response.server.repository.BaseRepositoryImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class StreamJsonResponseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamJsonResponseServerApplication.class, args);
	}

}

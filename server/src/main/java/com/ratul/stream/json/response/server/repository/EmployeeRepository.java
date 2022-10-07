package com.ratul.stream.json.response.server.repository;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import com.ratul.stream.json.response.server.entity.Employee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
	
	@QueryHints(
		value = {
			@QueryHint(name = "org.hibernate.fetchSize", value = Integer.MIN_VALUE + "" )
		}
	)
	@Query("SELECT e FROM Employee e")
	public Stream<Employee> streamAll();
}

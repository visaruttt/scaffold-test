package com.tvisarut.scaffold.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvisarut.scaffold.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByUsername(String username);
}

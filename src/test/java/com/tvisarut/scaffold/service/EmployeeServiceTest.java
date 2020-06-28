package com.tvisarut.scaffold.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeServiceTest {
	// unit test by using Mockito library && use the assertion from Junit
	@Autowired
	private EmployeeService service;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Test
	public void saveEmployeeTest() throws Exception {
		// mock data
		Employee mockEmployee = new Employee("Visarut", "T", "visarut", "P@ssw0rd");
		// Arrange then Act
		when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
		// Assert
		assertEquals(mockEmployee, service.saveEmployee(mockEmployee));
	}

	@Test
	public void getEmployeesTest() {
		// mock adding data new 2 employee
		when(employeeRepository.findAll()).thenReturn(Stream
				.of(new Employee("Visarut", "T", "visarut", "P@ssw0rd"),
						new Employee("mockfistname", "mocklastname", "mockUsername", "P@ssw0rd"))
				.collect(Collectors.toList()));
		// size must equal to 2 when query * in table
		assertEquals(2, service.getEmployees().size());
	}

	@Test
	public void getEmployeeByIdTest() {
		Long id = Long.valueOf(1);
		Employee mockEmployee = new Employee("VisarutMockInGet", "T", "visarut", "P@ssw0rd");
		// mock adding data new 1 employee
		when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));
		// query result must not null
		assertNotEquals(null, service.getEmployeeById(id));
	}

	@Test
	public void deleteEmployeeTest() {
		Employee mockEmployee = new Employee("VisarutMockForDelete", "T", "visarutDel", "P@ssw0rd");
		mockEmployee.setId(1l);
		when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);

		service.deleteEmployee(mockEmployee.getId());
		verify(employeeRepository, times(1)).deleteById(mockEmployee.getId());
	}

	@Test
	public void updateEmployeeTest() throws Exception {
		
		// mock data
		Employee mockEmployee = new Employee("Visarut", "T", "visarut", "P@ssw0rd");
		mockEmployee.setId(1l);
		// Arrange then Act
		when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
		
		Employee existEmployee = new Employee("VisarutUPDATE", "T", "visarutupdate", "P@ssw0rd");
		when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(existEmployee));
		// Arrange then Act
		when(employeeRepository.save(existEmployee)).thenReturn(existEmployee);
		// Assert
		assertNotEquals(mockEmployee, service.updateEmployee(mockEmployee.getId(), existEmployee));
		assertEquals(existEmployee, service.updateEmployee(mockEmployee.getId(), existEmployee));
	}
}

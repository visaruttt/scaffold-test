package com.tvisarut.scaffold.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.repository.EmployeeRepository;
import com.tvisarut.scaffold.util.JWTUtil;

@RunWith(JUnit4.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {
	// prepare for integration test

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JWTUtil jwtUtil;

	private UsernamePasswordAuthenticationToken token;
	private UserDetails user;
	private String accToken;
	private String contentType;
	private Long dupId;

	@Autowired
	private EmployeeRepository repository;

	@BeforeEach
	public void setup() {
		// initate data
		token = new UsernamePasswordAuthenticationToken("visarut", "P@ssw0rd");
		user = new User("visarut", "P@ssw0rd", new ArrayList<>());
		accToken = jwtUtil.generateToken(user);
		contentType = MediaType.APPLICATION_JSON + "";
		dupId = 10l;
	}

	@Test
	@Order(1)
	public void shouldNotAllowAccessToGetEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employee")).andExpect(status().isForbidden());
	}

	@Test
	@Order(2)
	public void shouldNotAllowAccessToCreateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/employee")).andExpect(status().isForbidden());
	}

	@Test
	@Order(3)
	public void shouldNotAllowAccessToUpdateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/employee")).andExpect(status().isForbidden());
	}

	@Test
	@Order(4)
	public void shouldNotAllowAccessToDeleteEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/employee")).andExpect(status().isForbidden());
	}

	@Test
	@Order(5)
	public void getEmployeesNoAuthAPITest() throws Exception {
		String response = mvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
	}

	@Test
	@Order(6)
	public void getEmployeesAPITest() throws Exception {
		String response = mvc
				.perform(get("/employee").contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken).content(""))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType)).andReturn().getResponse()
				.getContentAsString();
	}

	@Test
	@Order(7)
	public void getEmployeesByIdTest() throws Exception {

		String response = mvc
				.perform(get("/employee").contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken).param("id", "1").content(""))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType)).andReturn().getResponse()
				.getContentAsString();
	}

	@Test
	@Order(8)
	public void getEmployeesByIdNotFoundTest() throws Exception {
		String response = mvc
				.perform(get("/employee").contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken).param("id", "15").content(""))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.statusCode", is(404)))
				.andExpect(content().contentType(contentType)).andReturn().getResponse().getContentAsString();

	}

	@Test
	@Order(9)
	public void addEmployeeAPITest() throws Exception {
		String response = mvc
				.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken)
						.content("{\n" + "    \"firstname\": \"visarut\",\n" + "    \"lastname\": \"tttt\",\n"
								+ "    \"username\": \"visarutnewfortest\",\n" + "    \"password\": \"P@ssw0rd\"\n"
								+ "}"))
				.andExpect(status().isCreated()).andExpect(content().contentType(contentType)).andReturn().getResponse()
				.getContentAsString();
	}

	@Test
	@Order(10)
	public void addDuplicateEmployeeAPITest() throws Exception {
		String response = mvc
				.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken)
						.content("{\n" + "    \"firstname\": \"visarut\",\n" + "    \"lastname\": \"tttt\",\n"
								+ "    \"username\": \"visarutnewfortest\",\n" + "    \"password\": \"P@ssw0rd\"\n"
								+ "}"))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(contentType)).andReturn()
				.getResponse().getContentAsString();
	}

	@Test
	@Order(11)
	public void updateEmployeeAPITest() throws Exception {
		Employee emp = repository.findByUsername("visarutnewfortest");
		Long id = emp.getId();
		String response = mvc
				.perform(put("/employee/" + id).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken)
						.content("{\n" + "    \"firstname\": \"visarutEditing\",\n" + "    \"lastname\": \"tttt\",\n"
								+ "    \"username\": \"visarutnewfortest\",\n" + "    \"password\": \"P@ssw0rd\"\n"
								+ "}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstname", is("visarutEditing")))
				.andExpect(content().contentType(contentType)).andReturn().getResponse().getContentAsString();
	}

	@Test
	@Order(12)
	public void deleteEmployeeAPITest() throws Exception {
		Employee emp = repository.findByUsername("visarutnewfortest");
		this.dupId = emp.getId();
		String response = mvc
				.perform(delete("/employee/" + dupId).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken).content(""))
				.andExpect(status().isNoContent()).andReturn().getResponse().getContentAsString();
	}

	@Test
	@Order(13)
	public void deleteEmployeeNotFoundAPITest() throws Exception {
		String response = mvc
				.perform(delete("/employee/" + this.dupId).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + accToken).content(""))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
	}

	@Test
	@Order(14)
	public void updateEmployeeNotFoundAPITest() throws Exception {
		String response = mvc.perform(put("/employee/" + this.dupId).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accToken)
				.content("{\n" + "    \"firstname\": \"visarutEditing\",\n" + "    \"lastname\": \"tttt\",\n"
						+ "    \"username\": \"visarutnewfortest\",\n" + "    \"password\": \"P@ssw0rd\"\n" + "}"))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
	}
}

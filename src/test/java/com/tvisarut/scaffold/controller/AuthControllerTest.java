package com.tvisarut.scaffold.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldNotAllowAccessToGetEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employee")).andExpect(status().isForbidden());
	}

	@Test
	public void shouldNotAllowAccessToCreateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/employee")).andExpect(status().isForbidden());
	}

	@Test
	public void shouldNotAllowAccessToUpdateEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.put("/employee")).andExpect(status().isForbidden());
	}

	@Test
	public void shouldNotAllowAccessToDeleteEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/employee")).andExpect(status().isForbidden());
	}

//	@WithMockUser(username = "visarut3", password = "P@ssw0rd")
//	@Test
//	public void loginTest() throws Exception {
//		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
//
//		String response = mvc
//				.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//						.content("{\"username\":\"visarut3\",\"password\":\"P@ssw0rd\"}"))
//				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
//				.andExpect(jsonPath("$.accessToken", is(notNullValue()))).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.print("token " + response);// {"token":"1a3434a"}
//	}

//    /**
//     * Test the INVALID user 
//     * */
//    @Test (expected = AccessDeniedException.class)
//    public void testInvalidUser()
//    {
//        UserDetails userDetails = userDetailsService.loadUserByUsername ("admin");
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new GrantedAuthorityImpl("ROLE_INVALID"));
//        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        DemoService service = (DemoService) applicationContext.getBean("demoService");
//        service.method();
//    }
}

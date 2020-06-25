package com.tvisarut.scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

public class Main {
	// TODO customize HTTP response for more practical
	// TODO submit employee to database >> https://spring.io/guides/gs/accessing-data-mysql/
	// TODO refactor code 
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@GetMapping("/hello")
	String hello() {
		return "helloworld";
	}
}

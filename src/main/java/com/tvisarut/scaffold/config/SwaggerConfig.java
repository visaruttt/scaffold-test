package com.tvisarut.scaffold.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public Docket userApi() {

		return new Docket(DocumentationType.SWAGGER_2).directModelSubstitute(LocalDateTime.class, String.class)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
				.apiInfo(apiInfo()).select().build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Scaffold Visarut API")
				.contact(new Contact("Visarut Tirataworawan", "http://linkedin.com/in/visarut/",
						"visarut.tirataworawan@gmail.com"))
				.license("Apache License Version 2.0").version("2.0").build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(tokenAuth()).forPaths(PathSelectors.any()).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", AUTHORIZATION_HEADER, "header");
	}

	List<SecurityReference> tokenAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
	}

}
package com.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SwaggerConfig {

	private static final String API_KEY = "apiKey";

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {
		return new OpenAPI().components(new Components()
                .addSecuritySchemes(API_KEY,apiKeySecuritySchema()))
				.info(new Info().title("Legal Advisor application API").version(appVersion)
				.description(appDesciption).termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
	}

	public SecurityScheme apiKeySecuritySchema() {
		return new SecurityScheme().name("Authorization") // authorisation-token
				.description("TOKEN is required for authorization").in(SecurityScheme.In.HEADER)
				.type(SecurityScheme.Type.APIKEY);
	}

}
//@Configuration
//public class SwaggerConfig {
//	private static final String SCHEME_NAME = "basicAuth";
//    private static final String SCHEME = "basic";
//    
////	@Bean
////	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
////			@Value("${application-version}") String appVersion) {
////		return new OpenAPI().info(new Info().title("NICU application API").version(appVersion)
////				.description(appDesciption).termsOfService("http://swagger.io/terms/")
////				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
////	}
//	
//	private void addSecurity(OpenAPI openApi) {
//        var components = createComponents();
//        var securityItem = new SecurityRequirement().addList(SCHEME_NAME);
// 
//        openApi
//                .components(components)
//                .addSecurityItem(securityItem);
//    }
// 
//	 private Components createComponents() {
//	        var components = new Components();
//	        components.addSecuritySchemes(SCHEME_NAME, createSecurityScheme());
//	 
//	        return components;
//	    }
//	 
//	    private SecurityScheme createSecurityScheme() {
//	        return new SecurityScheme()
//	                .name(SCHEME_NAME)
//	                .type(SecurityScheme.Type.HTTP)
//	                .scheme(SCHEME);
//	    }
//}

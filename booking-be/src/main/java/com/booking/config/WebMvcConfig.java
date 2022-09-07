package com.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//	private final long MAX_AGE_SECS = 3600;
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedOrigins("http://localhost:3000", "http://dev.admin.ebyapaar.com", "http://dev.ebyapaar.com",
//						"https://ebyapaar.com","https://www.ebyapaar.com","https://www.admin.ebyapaar.com", "https://admin.ebyapaar.com")
//				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//				.allowedHeaders("*").allowCredentials(true).maxAge(MAX_AGE_SECS);
//	}
//}
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
				.allowCredentials(true).maxAge(MAX_AGE_SECS);
	}
}

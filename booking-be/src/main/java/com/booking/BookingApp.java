package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.booking.config.AppConfig;


@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
@ComponentScan({ "com.booking" })
public class BookingApp {
	public static void main(String[] args) {
		SpringApplication.run(BookingApp.class, args);
	}
}

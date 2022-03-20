package com.mfturkcan.addressbooksystemrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AddressBookSystemRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookSystemRestApplication.class, args);
	}
}
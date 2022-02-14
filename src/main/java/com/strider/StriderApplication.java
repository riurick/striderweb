package com.strider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication(scanBasePackages={"com.strider"})
public class StriderApplication {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		SpringApplication.run(StriderApplication.class, args);
	}



}



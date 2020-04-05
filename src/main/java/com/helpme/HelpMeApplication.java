package com.helpme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HelpMeApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HelpMeApplication.class, args);
	}

}

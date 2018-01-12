package com.ross53.cobar;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configurable
@SpringBootApplication
public class CobarApplication {

	public static void main(String[] args) {
		SpringApplication.run(CobarApplication.class, args);
	}
}

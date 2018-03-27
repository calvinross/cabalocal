package com.ross53.cobar;

import com.ross53.cobar.controller.OrderInfoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Configurable
@SpringBootApplication
public class CobarApplication {

	@Component
	static class Runner implements CommandLineRunner{

		@Autowired
		private OrderInfoController orderInfoController;

		@Override
		public void run(String... args) throws Exception{
			while (true) {
				//System.out.println("runner is running....");
			}
		}


	}

	public static void main(String[] args) {
		SpringApplication.run(CobarApplication.class, args);
	}
}

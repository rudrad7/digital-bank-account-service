package com.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalBankAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankAccountServiceApplication.class, args);
		System.out.println("Account service is started on : 8083");
	}

}

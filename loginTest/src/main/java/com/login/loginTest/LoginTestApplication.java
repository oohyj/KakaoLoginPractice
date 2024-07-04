package com.login.loginTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LoginTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginTestApplication.class, args);
	}

}

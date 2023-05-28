package com.mju.ssoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
class SsoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoClientApplication.class, args);
	}
}

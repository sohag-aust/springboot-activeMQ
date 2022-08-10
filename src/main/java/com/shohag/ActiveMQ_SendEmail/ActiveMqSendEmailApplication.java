package com.shohag.ActiveMQ_SendEmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActiveMqSendEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqSendEmailApplication.class, args);
	}

}

package com.sample.jms.ibmmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sample.jms.ibmmq"})
public class SpringJmsIbmmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsIbmmqApplication.class, args);
	}
}

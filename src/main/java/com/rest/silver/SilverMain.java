package com.rest.silver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.rest.silver"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SilverMain {
	public static void main(String[] args) {
		SpringApplication.run(SilverMain.class, args);
	}
}

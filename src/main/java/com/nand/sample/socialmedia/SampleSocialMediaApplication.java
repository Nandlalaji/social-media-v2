package com.nand.sample.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.nand.sample.socialmedia"})
public class SampleSocialMediaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SampleSocialMediaApplication.class, args);
	}

}

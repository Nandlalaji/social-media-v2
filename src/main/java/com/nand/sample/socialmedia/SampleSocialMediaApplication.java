package com.nand.sample.socialmedia;

import javax.persistence.EntityManagerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.nand.sample.socialmedia.domain.UserDTO;
import com.nand.sample.socialmedia.model.User;

@SpringBootApplication
@ComponentScan({"com.nand.sample.socialmedia"})
public class SampleSocialMediaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SampleSocialMediaApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper(EntityManagerFactory emFactory) {
        return new ModelMapper();
    }

}

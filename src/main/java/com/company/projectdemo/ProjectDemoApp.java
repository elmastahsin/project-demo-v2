package com.company.projectdemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDemoApp.class, args);
    }
    ModelMapper modelMapper = new ModelMapper();
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }


}

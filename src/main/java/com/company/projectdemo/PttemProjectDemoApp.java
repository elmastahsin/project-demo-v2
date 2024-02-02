package com.company.projectdemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PttemProjectDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(PttemProjectDemoApp.class, args);
    }
    ModelMapper modelMapper = new ModelMapper();
    @Bean
    public ModelMapper mapper(){

        return new ModelMapper();
    }


}

package com.company.projectdemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "PttemKart App",
                description = "PttemKart App API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Tahsin Elmas",
                        email = "tahsin.elmas@pttem.com",
                        url = "https://www.pttemkart.com/"
                )

        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local server"
                ),
                @Server(
                        url = "https://pttemkart.com",
                        description = "Production server"
                ),
                @Server(
                        url = "https://qa.pttemkart.com",
                        description = "Testing server"
                )
        }
)
@SpringBootApplication
public class ProjectDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDemoApp.class, args);
    }

    ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }


}

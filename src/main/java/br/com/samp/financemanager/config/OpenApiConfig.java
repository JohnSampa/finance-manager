package br.com.samp.financemanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Finance Manager API")
                        .description("Finance Manager é uma aplicação back-end " +
                                "de gerenciamento de gastos e finanças pessoais")
                        .contact(
                                new Contact()
                                        .name("Jonathan Samapaio")
                                        .email("aurijona192@gmail.com")
                        )
        );
    }
}

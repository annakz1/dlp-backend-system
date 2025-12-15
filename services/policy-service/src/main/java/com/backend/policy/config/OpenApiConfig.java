package com.backend.policy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Policy Service (Internal API)",
                version = "1.0",
                description = "Managing policies composed of DTs (Data Sets / DSs)"
        )
)
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI();
        }
}


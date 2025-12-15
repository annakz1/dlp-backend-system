package com.backend.scanner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Scanner Service (External API)",
                version = "1.0",
                description = "Scanning input strings and producing inspection results"
        )
)
public class OpenApiConfig {

}


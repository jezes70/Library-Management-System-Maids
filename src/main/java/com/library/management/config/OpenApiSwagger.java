package com.library.management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(name = "", email = "jezes70@gmail.com", url = ""),
                description = "simple sales management system, which should include product management,\n" +
                        "client management, sales operations management, reporting, and user authentication features.",
                title = "LIBRARY MANAGEMENT",
                version = "1.0",
                license = @License(name = "Apache License", url = "https://www.apache.org/licenses/LICENSE-2"),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "DEV ENV",
                        url = "http://localhost:8082"
                ),

        },
        security = {
                @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Bearer Authentication")
        }
)

public class OpenApiSwagger {


}

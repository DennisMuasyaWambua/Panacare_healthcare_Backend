package com.panacare.frontend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Value("${app.base.url}")
    private String baseUrl;

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(baseUrl);
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Alex Kago");
        myContact.setEmail("alex@rensoft.co.ke");

        Info information = new Info()
                .title("Panacare Health System API")
                .version("1.0")
                .description("This API exposes endpoints for panacare app.")
                .contact(myContact);
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(information).servers(List.of(server));
    }
}

package com.finances.finances.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final SecurityScheme bearerSecurityRequirement =
      new SecurityScheme()
          .name("Bearer Authentication")
          .type(SecurityScheme.Type.HTTP)
          .bearerFormat("JWT")
          .scheme("bearer");

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .info(
            new Info()
                .title("Finances API")
                .version("v1")
                .contact(
                    new Contact()
                        .url("https://www.linkedin.com/in/joaopaulo-chaves/")
                        .email("jpcchaves@outlook.com"))
                .description("REST API built to manage expenses")
                .termsOfService("https://jpcchaves-dev.netlify.app")
                .license(new License().name("MIT").url("https://jpcchaves-dev.netlify.app")))
        .schemaRequirement("Bearer Authentication", bearerSecurityRequirement);
  }
}

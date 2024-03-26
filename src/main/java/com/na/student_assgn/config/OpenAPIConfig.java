package com.na.student_assgn.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
@OpenAPIDefinition(
		   info = @Info(
				   contact = @Contact(
						   name = "sathish",
						   email = "sat@gmail.com",
						   url = "http://local"
				    ),
				   description = "open API docmentation",
				   title = "open API specification - Sathish",
				   termsOfService = "Terms of Service"
//				   version = "1.0"
				   ),
		   servers = {
				   @Server(
						   description = "Local ENV",
						   url = "http://localhost:8081"
						   ),
				   @Server(
						   description = "Production ENV",
						   url = "http://localhost:8081"
						   )
		   },
	        security = {
	                @SecurityRequirement(
	                        name = "bearerAuth"
	                )
	        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

//@Configuration
public class OpenAPIConfig {

//	@Bean
//	public OpenAPI openAPI() {
//		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//				.components(new Components().addSecuritySchemes("bearerAuth",
//						new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer")
//								.bearerFormat("JWT")))
//				.info(new Info().title("Demo Spring Boot REST API Project")
//						.description("Based on users and leads.<br>" + "Users can manage their leads.").version("1.0"));
//	}
	
}

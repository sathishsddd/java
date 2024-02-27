package com.na.student_assgn.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
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
						   url = "http://localhost:8080"
						   ),
				   @Server(
						   description = "Production ENV",
						   url = "http://localhost:8081"
						   )
		   }
)
public class OpenAPIConfig {

}

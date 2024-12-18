package com.tsspringexperience.config;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration 
public class SwaggerConfig {
 
	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	    		.addServersItem(new Server().url("https://apps.myneorcha.com/dedge").description("Production"))
	    		.addServersItem(new Server().url("http://localhost:8081/dedge").description("Localhost"))
	    		
                .info(new Info().title("D-edge API Documentation").description("This documentation describe Neorcha service that call D-edge service."));
                
	} 
	
	@Bean
	public GroupedOpenApi neorchafbOpenApi() {
		String paths[] = {"/vback1/**", "/vfront1/**"};
		return GroupedOpenApi.builder().setGroup("dedge").pathsToMatch(paths)
				.build();
	}

}
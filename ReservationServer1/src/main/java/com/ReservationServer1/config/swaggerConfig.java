package com.ReservationServer1.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// http://localhost:8090/swagger-ui/index.html 접속
@Configuration
public class swaggerConfig {

	@Bean
	protected GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("Reservation Server1").pathsToMatch("/**").build();
	}

	@Bean
	protected OpenAPI springShopOpenAPI() {
		return new OpenAPI().info(
				new Info().title("Reservation API").description("Reservation 프로젝트 API 명세서입니다.").version("v0.0.1"));
	}

}

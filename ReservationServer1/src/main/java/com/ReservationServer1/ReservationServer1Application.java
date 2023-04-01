package com.ReservationServer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;




@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Resevation Server API",
				version = "1.0.0",
				description = "Connect",
				contact = @Contact(
					name = "Jaemin Mok",
					email = "bame@naver.com"
				)
		)
)
// http://localhost:8090/swagger-ui/index.html 접속
public class ReservationServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServer1Application.class, args);
	}

}

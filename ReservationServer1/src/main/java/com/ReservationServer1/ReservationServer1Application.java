package com.ReservationServer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class ReservationServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServer1Application.class, args);
	}

}

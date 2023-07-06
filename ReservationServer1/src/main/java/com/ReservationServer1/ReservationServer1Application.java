package com.ReservationServer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.ReservationServer1.service", "com.ReservationServer1.controller", "com.ReservationServer1.DAO", "com.ReservationServer1.config"})
public class ReservationServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServer1Application.class, args);
	}

}

package com.hotelmanagement.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class HotelManagementBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagementBookingServiceApplication.class, args);
	}

}

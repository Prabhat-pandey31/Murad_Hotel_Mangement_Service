package com.hotelmanagement.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelManagementApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagementApiGatewayApplication.class, args);
	}

}

package com.hotelmanagement.user;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.hotelmanagement.user.entity.User;
import com.hotelmanagement.user.service.UserService;
import com.hotelmanagement.user.utility.Constants.UserRole;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class HotelManagementUserServiceApplication implements CommandLineRunner {

	private final Logger LOG = LoggerFactory.getLogger(HotelManagementUserServiceApplication.class);

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(HotelManagementUserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User admin = this.userService.getUserByEmailIdAndRole("demo.admin@demo.com", UserRole.ADMIN.value());

		if (admin == null) {

			LOG.info("Admin not found in system, so adding default admin");

			User user = new User();
			user.setEmailId("demo.admin@demo.com");
			user.setPassword(Base64.getEncoder().encodeToString("123456".getBytes()));
			user.setRole(UserRole.ADMIN.value());

			this.userService.registerUser(user);

		}

	}

}

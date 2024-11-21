package com.hotelmanagement.hotel.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelmanagement.hotel.dto.CommanApiResponse;
import com.hotelmanagement.hotel.dto.UsersResponseDto;

@Component
@FeignClient(name = "hotel-management-user-service", url = "http://localhost:8080/api/user")
public interface UserService {

	@GetMapping("id")
	public UsersResponseDto fetchUser(@RequestParam("userId") int userId);
	
	@PutMapping("update/hotel/admin")
	public CommanApiResponse updateHotelAdmin(@RequestParam("userId") int userId,
			@RequestParam("hotelId") int hotelId);

	
}

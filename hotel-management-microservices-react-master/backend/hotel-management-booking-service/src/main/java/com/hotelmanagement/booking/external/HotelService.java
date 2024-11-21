package com.hotelmanagement.booking.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelmanagement.booking.dto.HotelResponseDto;


@Component
@FeignClient(name = "hotel-management-hotel-service", url = "http://localhost:8080/api/hotel")
public interface HotelService {
	
	@GetMapping("id")
	public HotelResponseDto fetchHotel(@RequestParam("hotelId") int hotelId);


}

package com.hotelmanagement.hotel.dto;

import com.hotelmanagement.hotel.entity.Hotel;

public class HotelResponseDto extends CommanApiResponse {
	
	private Hotel hotel;

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	

}

package com.hotelmanagement.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.hotel.dao.HotelDao;
import com.hotelmanagement.hotel.entity.Hotel;
import com.hotelmanagement.hotel.entity.Location;

@Service
public class HotelService {

	@Autowired
	private HotelDao hotelDao;

	public Hotel addHotel(Hotel hotel) {
		return hotelDao.save(hotel);
	}
	
	public List<Hotel> fetchAllHotels() {
		return hotelDao.findAll();
	}
	
	public List<Hotel> fetchHotelsByLocation(Location locationId) {
		return hotelDao.findByLocation(locationId);
	}
	
	public Hotel fetchHotel(int hotelId) {
		return hotelDao.findById(hotelId).get();
	}

}

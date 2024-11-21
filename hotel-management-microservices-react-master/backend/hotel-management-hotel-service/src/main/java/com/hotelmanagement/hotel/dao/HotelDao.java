package com.hotelmanagement.hotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelmanagement.hotel.entity.Hotel;
import com.hotelmanagement.hotel.entity.Location;

@Repository
public interface HotelDao extends JpaRepository<Hotel, Integer> {
	
	List<Hotel> findByLocation(Location locationId);
	
}

package com.hotelmanagement.hotel.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotelmanagement.hotel.dao.HotelFacilityDao;
import com.hotelmanagement.hotel.entity.HotelFacility;

@Service
public class HotelFacilityService {
	
	@Autowired
	private HotelFacilityDao hotelFacilityDao;
	
	public List<HotelFacility> getHotelFacilitiesByHotelId(int hotelId) {
		return this.hotelFacilityDao.findByHotelId(hotelId);
	}
	
	public HotelFacility addFacility(HotelFacility hotelFacility) {
	    return this.hotelFacilityDao.save(hotelFacility);
	}

}

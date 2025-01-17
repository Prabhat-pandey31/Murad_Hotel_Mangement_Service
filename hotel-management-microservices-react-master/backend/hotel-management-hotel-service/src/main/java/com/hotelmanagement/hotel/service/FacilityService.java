package com.hotelmanagement.hotel.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.hotel.dao.FacilityDao;
import com.hotelmanagement.hotel.entity.Facility;

@Service
public class FacilityService {
	
	@Autowired
	private FacilityDao facilityDao;
	
	public Facility addFacility(Facility facility) {
		return facilityDao.save(facility);
	}
	
	public Set<Facility> fetchAllFacilities() {
		return new HashSet<Facility>(facilityDao.findAll());	
	}
	
	public Facility getFacilityById(int facilityId) {
		return facilityDao.findById(facilityId).get();
	}
 
}

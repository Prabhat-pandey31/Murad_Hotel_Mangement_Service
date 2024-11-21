package com.hotelmanagement.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotelmanagement.hotel.entity.Facility;

@Repository
public interface FacilityDao extends JpaRepository<Facility, Integer> {

}

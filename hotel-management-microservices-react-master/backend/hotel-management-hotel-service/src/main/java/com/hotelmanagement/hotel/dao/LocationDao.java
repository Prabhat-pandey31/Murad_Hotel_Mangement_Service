package com.hotelmanagement.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotelmanagement.hotel.entity.Location;

@Repository
public interface LocationDao extends JpaRepository<Location, Integer> {

}

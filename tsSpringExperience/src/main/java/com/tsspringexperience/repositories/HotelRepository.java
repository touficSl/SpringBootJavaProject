package com.tsspringexperience.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tsspringexperience.entities.Hotel;

@RepositoryRestResource
public interface HotelRepository extends JpaRepository<Hotel, String>, JpaSpecificationExecutor<Hotel> {

    @Query(value = "SELECT * FROM hotel as h where h.hotel_id = :HotelId", nativeQuery = true)
    Hotel findByHotelId(@Param("HotelId") String HotelId); 
    
}
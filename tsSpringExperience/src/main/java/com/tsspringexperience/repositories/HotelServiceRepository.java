package com.tsspringexperience.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tsspringexperience.entities.HotelServices;

@RepositoryRestResource
public interface HotelServiceRepository extends JpaRepository<HotelServices, Integer> {

	 @Query(value = "SELECT * FROM hotel_service as hs, service as s where hs._hotel_id = :HotelId and s.service_name= :serviceName and s.id = hs.service_id and enabled = 1", nativeQuery = true)
	 HotelServices findHotelServiceByHotelId(@Param("HotelId") String HotelId, @Param("serviceName") String serviceName); 
}
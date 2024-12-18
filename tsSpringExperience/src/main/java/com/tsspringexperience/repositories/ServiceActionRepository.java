package com.tsspringexperience.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tsspringexperience.entities.ServiceAction;

@RepositoryRestResource
public interface ServiceActionRepository extends JpaRepository<ServiceAction, Integer> {

	@Query(value = "FROM ServiceAction WHERE actionPath = :actionPath and hotelService.HotelId = :HotelId and hotelService.service.service_name = :serviceName and hotelService.enabled = 1 and enabled = 1", nativeQuery = false)
	ServiceAction findServiceActionByHotelId(@Param("HotelId") String HotelId, @Param("serviceName") String serviceName, @Param("actionPath") String actionPath);
	
}
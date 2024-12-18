package com.tsspringexperience.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tsspringexperience.entities.EmailProps;


@RepositoryRestResource
public interface EmailPropsRepository extends JpaRepository<EmailProps, Integer> {

    @Query(value = "SELECT * FROM email_props where hotel_id = :HotelId", nativeQuery = true)
    List<EmailProps> findByHotelId(@Param("HotelId") String HotelId); 

    @Query(value = "SELECT * FROM email_props where is_default = 1", nativeQuery = true)
    List<EmailProps> findDefault(); 
}

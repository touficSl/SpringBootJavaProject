package com.tsspringexperience.services;

import com.tsspringexperience.entities.EmailProps;

public interface EmailPropsService {
 
	EmailProps findByHotelId(String HotelId);
}

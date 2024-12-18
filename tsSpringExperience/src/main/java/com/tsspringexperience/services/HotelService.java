package com.tsspringexperience.services;

import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.HotelServices;
import com.tsspringexperience.entities.ServiceAction;
 
public interface HotelService {

	Hotel getOne(String code);

	Hotel save(Hotel hotel);

	Hotel findByHotelId(String HotelId);

	Hotel findByDedgeHotelCode(String dedgeHotelCode);

	HotelServices findHotelServiceByHotelId(String HotelId);

	ServiceAction findServiceActionByHotelId(String HotelId, String actionPath);
	
}

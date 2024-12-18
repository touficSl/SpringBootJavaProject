package com.tsspringexperience.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tsspringexperience.entities.Hotel;
import com.tsspringexperience.entities.HotelServices;
import com.tsspringexperience.entities.ServiceAction;
import com.tsspringexperience.repositories.HotelRepository;
import com.tsspringexperience.repositories.HotelServiceRepository;
import com.tsspringexperience.repositories.ServiceActionRepository;
import com.tsspringexperience.utils.Constants;

@Service("hotelService")
@Repository 
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private HotelServiceRepository hotelServiceRepository; 

	@Autowired
	private ServiceActionRepository serviceActionRepository; 

	@Override
	public Hotel getOne(String code) {
		return hotelRepository.findByHotelId(code);
	}

	@Override
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	@Override
	public HotelServices findHotelServiceByHotelId(String HotelId) {
		return hotelServiceRepository.findHotelServiceByHotelId(HotelId, Constants.SERVICE_NAME);
	}

	@Override
	public ServiceAction findServiceActionByHotelId(String HotelId, String actionPath) {
		return serviceActionRepository.findServiceActionByHotelId(HotelId, Constants.SERVICE_NAME, actionPath);
	}

	@Override
	public Hotel findByHotelId(String HotelId) {
		return hotelRepository.findByHotelId(HotelId);
	}

	@Override
	public Hotel findByDedgeHotelCode(String dedgeHotelCode) {
		return hotelRepository.findByDedgeHotelCode(dedgeHotelCode);
	}

}

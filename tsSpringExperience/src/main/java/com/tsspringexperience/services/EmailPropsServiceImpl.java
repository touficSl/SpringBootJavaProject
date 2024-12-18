package com.tsspringexperience.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tsspringexperience.entities.EmailProps;
import com.tsspringexperience.repositories.EmailPropsRepository;

@Service("emailPropsService")
@Repository
@Transactional
public class EmailPropsServiceImpl implements EmailPropsService{
	@Autowired
	private EmailPropsRepository emailPropsRepository;
  
	@Override
	public EmailProps findByHotelId(String HotelId) {
		List<EmailProps> emailProps = emailPropsRepository.findByHotelId(HotelId);
		
		if (emailProps == null || emailProps.size() == 0)
			emailProps = emailPropsRepository.findDefault();

		/* This case should not happen but just to prevent returning null value  */
		if (emailProps == null || emailProps.size() == 0)
			emailProps = emailPropsRepository.findAll();
			
		return emailProps.get(0);
	}
}

package com.tsspringexperience.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tsspringexperience.entities.DeviceType;
import com.tsspringexperience.repositories.DeviceTypeRepository;

@Service("deviceTypeService")
@Repository 
public class DeviceTypeServiceImpl implements DeviceTypeService{
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DeviceTypeRepository deviceTypeRepository;
	
	@Override
	public DeviceType checkDeviceType(String deviceTypeId) {
		List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
		
		for(DeviceType deviceType : deviceTypeList) {
			if(deviceTypeId.equals(deviceType.getDeviceTypeId())) {
				return deviceType;
			}
		}
		return null;
	}

}

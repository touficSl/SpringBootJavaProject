package com.tsspringexperience.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsspringexperience.entities.ImageEntity;
import com.tsspringexperience.repositories.ImageEntityRepository;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageEntityRepository imageEntityRepository;

	@Override
	public List<ImageEntity> findImagesByCodeByEnum(String code, int enumId) {
		return imageEntityRepository.findImagesByCodeByEnum(code, enumId+"");
	}
	
}

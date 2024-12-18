package com.tsspringexperience.services;

import java.util.List;

import com.tsspringexperience.entities.ImageEntity;

public interface ImageService { 
	
	public final static int ROOM = 2;

	List<ImageEntity> findImagesByCodeByEnum(String code, int enumId);
	
}

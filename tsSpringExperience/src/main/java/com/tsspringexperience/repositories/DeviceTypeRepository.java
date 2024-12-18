package com.tsspringexperience.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tsspringexperience.entities.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
	// CRUD refers Create, Read, Update, Delete
}

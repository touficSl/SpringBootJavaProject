package com.tsspringexperience.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tsspringexperience.entities.ImageEntity;

@RepositoryRestResource
public interface ImageEntityRepository extends JpaRepository<ImageEntity, String>, JpaSpecificationExecutor<ImageEntity> {
	
	@Query(value = "FROM ImageEntity WHERE code = :code AND enumId = :enumId ORDER BY id DESC")
	public List<ImageEntity> findImagesByCodeByEnum(@Param("code") String code, @Param("enumId") String enumId);
	
}
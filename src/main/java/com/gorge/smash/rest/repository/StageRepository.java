package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Stage;

@RepositoryRestResource(collectionResourceRel = "stage", path = "stage")
public interface StageRepository extends JpaRepository<Stage, Long>
{
	public Stage findByNumber(Integer number);
	
	public Boolean existsByNumber(Integer number);
}

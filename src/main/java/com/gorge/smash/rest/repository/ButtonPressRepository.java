package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.ButtonPress;

@RepositoryRestResource(collectionResourceRel = "buttonpress", path = "buttonpress")
public interface ButtonPressRepository extends JpaRepository<ButtonPress, Long>
{
	public Boolean existsByName(String name);

}

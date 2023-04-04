package com.gorge.smash.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.ButtonPress;

@RepositoryRestResource(collectionResourceRel = "buttonpress", path = "buttonpress")
public interface ButtonPressRepository extends JpaRepository<ButtonPress, Long>
{
	public List<ButtonPress> findByTimestampBetween(Long start, Long end);
	
	public Boolean existsByName(String name);
	
	public ButtonPress findFirstByNameOrderByTimestampDesc(String name);
	
	public List<ButtonPress> findByNameAndTimestampBetween(String name, Long start, Long end);

}

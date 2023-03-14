package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.gorge.smash.model.entity.Button;

@RepositoryRestResource(collectionResourceRel = "button", path = "button")
public interface ButtonRepository extends JpaRepository<Button, Long>
{
	public Button findByName(String name);

	public Boolean existsByName(String name);

	@Modifying
	@Transactional
	@Query(value = "UPDATE button SET count=count+1 WHERE name = :name", nativeQuery = true)
	public void incrButtonCount(@Param("name") String name);
}

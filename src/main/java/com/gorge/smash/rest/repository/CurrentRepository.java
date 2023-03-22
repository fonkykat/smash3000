package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.Current;

@RepositoryRestResource(collectionResourceRel = "current", path = "current")
public interface CurrentRepository extends JpaRepository<Current, Long>
{
	public Current getById(Integer id);
}

package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Chapter;

@RepositoryRestResource(collectionResourceRel = "chapter", path = "chapter")
public interface ChapterRepository extends JpaRepository<Chapter, Long>
{
	public Chapter findByNumber(Integer number);
	
	public Boolean existsByNumber(Integer number);
}

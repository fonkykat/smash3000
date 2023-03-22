package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.Current;
import com.gorge.smash.model.entity.Stats;

@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface StatsRepository extends JpaRepository<Stats, Long>
{
	
}

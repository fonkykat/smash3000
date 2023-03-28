package com.gorge.smash.rest.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Concert;

@RepositoryRestResource(collectionResourceRel = "concert", path = "concert")
public interface ConcertRepository extends JpaRepository<Concert, Long>
{
	public Concert findByDate(Date date);
	
	public Concert existsByDate(Date date);
}

package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.LGMXSong;

@RepositoryRestResource(collectionResourceRel = "lgmxsong", path = "lgmxsong")
public interface LGMXSongRepository extends JpaRepository<LGMXSong, Long>
{
	public LGMXSong findByTitle(String title);
	
	public Boolean existsByTitle(String title);
}

package com.gorge.smash.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.Current;
import com.gorge.smash.model.entity.Text;

@RepositoryRestResource(collectionResourceRel = "text", path = "text")
public interface TextRepository extends JpaRepository<Text, Long>
{
	public Text getById(Integer id);
}

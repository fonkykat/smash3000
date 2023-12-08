package com.gorge.smash.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chapter")
public class Chapter
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToMany
	List<Stage> stages = new ArrayList<Stage>();
	
	@ManyToOne
	Song song;

	public Chapter()
	{
	}
	
	
	public Chapter(Song song) {
		super();
		this.song = song;
	}

	public Chapter(List<Stage> stages) {
		super();
		this.stages = stages;
	}

	public Chapter(Stage stage){
		super();
		this.stages = new ArrayList<Stage>();
		this.stages.add(stage);
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	
	public List<Stage> getStages() {
		return stages;
	}


	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}


	public Song getSong() {
		return song;
	}


	public void setSong(Song song) {
		this.song = song;
	}
	
	
	

}

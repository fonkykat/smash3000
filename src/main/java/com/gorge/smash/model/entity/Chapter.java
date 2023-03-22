package com.gorge.smash.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gorge.smash.util.Gamepad;

@Entity
@Table(name = "chapter")
public class Chapter
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Basic
	private Integer number;
	
	@Basic
	private String title;
	
	@Basic
	private Gamepad type;
	

	public Chapter()
	{
	}
	

	public Chapter(Integer number, String title, Gamepad type) {
		super();
		this.number = number;
		this.title = title;
		this.type = type;
	}



	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getNumber()
	{
		return number;
	}

	public void setNumber(Integer number)
	{
		this.number = number;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Gamepad getType() {
		return type;
	}


	public void setType(Gamepad type) {
		this.type = type;
	}
	
	

}

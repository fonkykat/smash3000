package com.gorge.smash.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current")
public class Current
{
	@Basic
	@Id
	private Integer id;

	@Basic
	private Integer number;

	public Current()
	{
	}
	
	public Current(Integer number)
	{
		this.number = number;
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

}

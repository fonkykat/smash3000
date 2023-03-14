package com.gorge.smash.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "button")
public class Button
{
	@Basic
	@Id
	private String name;

	@Basic
	private Integer count;

	public Button()
	{
	}

	public Button(String name, Integer count)
	{
		super();
		this.name = name;
		this.count = count;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

}

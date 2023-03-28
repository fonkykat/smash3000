package com.gorge.smash.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "concert")
public class Concert
{
	@Basic
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ElementCollection
	private Set<String> ips = new HashSet<String>();
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Basic
	private String location;
	
	@Basic
	private String name;
	

	public Concert()
	{
		super();
	}
	
	@PrePersist
	  protected void onCreate() {
		date = new Date();
	  }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<String> getIps() {
		return ips;
	}

	public void setIps(Set<String> ips) {
		this.ips = ips;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}

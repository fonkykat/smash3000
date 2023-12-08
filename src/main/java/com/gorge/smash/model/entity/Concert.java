package com.gorge.smash.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private Integer id;

	@ElementCollection
	private Set<String> ips = new HashSet<String>();
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Basic
	private String location;
	
	@Basic
	private String name;
	
	@ManyToMany
	List<Chapter> events = new ArrayList<Chapter>();
	
	@ManyToMany
	List<Song> setlist = new ArrayList<Song>();

	public Concert()
	{
		super();
		this.setlist = new ArrayList<>();
	}
	
	@PrePersist
	  protected void onCreate() {
		date = new Date();
	  }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public List<Chapter> getEvents() {
		return events;
	}

	public void setEvents(List<Chapter> events) {
		this.events = events;
	}

	public List<Song> getSetlist() {
		return setlist;
	}

	public void setSetlist(List<Song> setlist) {
		this.setlist = setlist;
	}	
	
}

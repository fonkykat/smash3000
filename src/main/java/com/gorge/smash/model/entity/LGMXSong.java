package com.gorge.smash.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lgmxsong")
public class LGMXSong
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Basic
	@Transient
	private Integer order;
	
	@Basic
	private String title;

	@Basic
	private Integer bpm;
	
	@Basic
	private String color1;
	
	@Basic
	private String color2;
	
	@Basic
	private String color3;
	
	@Basic
	private Boolean synchro;
	
	@Basic
	private String notes;
	
	public LGMXSong()
	{
	}
	
	

	public LGMXSong(String title) {
		super();
		this.title = title;
	}

	public LGMXSong(String title, Integer bpm, String color1, String color2, String color3, Boolean synchro,
			String notes) {
		super();
		this.title = title;
		this.bpm = bpm;
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.synchro = synchro;
		this.notes = notes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBpm() {
		return bpm;
	}

	public void setBpm(Integer bpm) {
		this.bpm = bpm;
	}

	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	public String getColor3() {
		return color3;
	}

	public void setColor3(String color3) {
		this.color3 = color3;
	}

	public Boolean getSynchro() {
		return synchro;
	}

	public void setSynchro(Boolean synchro) {
		this.synchro = synchro;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}

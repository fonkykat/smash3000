package com.gorge.smash.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "stats")
public class Stats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Basic
	private String chapter_name;
	
	@Basic
	private Integer chapter_number;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date savedOn;
	
	@Basic
	private Integer A;
	
	@Basic
	private Integer B;
	
	@Basic
	private Integer X;
	
	@Basic
	private Integer Y;
	
	@Basic
	private Integer SHAKE;
	
	
	@PrePersist
	  protected void onCreate() {
		savedOn = new Date();
	  }

	

	public Stats() {
		super();
	}
	

	public Stats(String chapter_name, Integer chapter_number, Integer a, Integer b, Integer x, Integer y,
			Integer sHAKE) {
		super();
		this.chapter_name = chapter_name;
		this.chapter_number = chapter_number;
		A = a;
		B = b;
		X = x;
		Y = y;
		SHAKE = sHAKE;
	}



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getSavedOn() {
		return savedOn;
	}


	public void setSavedOn(Date savedOn) {
		this.savedOn = savedOn;
	}
	

	public String getChapter_name() {
		return chapter_name;
	}



	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}



	public Integer getChapter_number() {
		return chapter_number;
	}



	public void setChapter_number(Integer chapter_number) {
		this.chapter_number = chapter_number;
	}




	public Integer getA() {
		return A;
	}


	public void setA(Integer a) {
		A = a;
	}


	public Integer getB() {
		return B;
	}


	public void setB(Integer b) {
		B = b;
	}


	public Integer getX() {
		return X;
	}


	public void setX(Integer x) {
		X = x;
	}


	public Integer getY() {
		return Y;
	}


	public void setY(Integer y) {
		Y = y;
	}


	public Integer getSHAKE() {
		return SHAKE;
	}



	public void setSHAKE(Integer sHAKE) {
		SHAKE = sHAKE;
	}
	
	
	
	
	
}

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
	
	@ManyToOne
	@JoinTable(name="chapter_stats")
	private Chapter chapter;
	
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
	
	
	@PrePersist
	  protected void onCreate() {
		savedOn = new Date();
	  }

	

	public Stats() {
		super();
	}
	
	
	public Stats(Chapter chapter, Integer a, Integer b, Integer x, Integer y) {
		super();
		this.chapter = chapter;
		A = a;
		B = b;
		X = x;
		Y = y;
	}



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Chapter getChapter() {
		return chapter;
	}


	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}


	public Date getSavedOn() {
		return savedOn;
	}


	public void setSavedOn(Date savedOn) {
		this.savedOn = savedOn;
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
	
	
	
}

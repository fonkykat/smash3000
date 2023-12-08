package com.gorge.smash.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gorge.smash.util.Gamepad;

@Entity
@Table(name = "stage")
public class Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Basic
	private String title;
	
	@Basic
	private Integer number;
	
	@Basic
	private Gamepad type;
	
	@OneToOne
	private Stage next;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Gamepad getType() {
		return type;
	}

	public void setType(Gamepad type) {
		this.type = type;
	}

	public Stage getNext() {
		return next;
	}

	public void setNext(Stage next) {
		this.next = next;
	}
	
	
	
	
	

}

package com.gorge.smash.util;

public class TempoError {
	
	private Long admin;
	
	private Double average;
	
	private Double error;

	public TempoError() {
		super();
	}

	public TempoError(Long admin, Double crowdAverage, Double error) {
		super();
		this.admin = admin;
		this.average = crowdAverage;
		this.error = error;
	}

	public Long getAdmin() {
		return admin;
	}

	public void setAdmin(Long admin) {
		this.admin = admin;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}
	
	

}

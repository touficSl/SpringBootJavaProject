package com.tsspringexperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="dedge_rate_policy")
public class RatePolicy {

	@JsonIgnore
	@Id
	@Column(name="id")
	private int id;

	@JsonIgnore
	@Column(name="rate_id")
	private int rateId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	public RatePolicy() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRateId() {
		return rateId;
	}

	public void setRateId(int rateId) {
		this.rateId = rateId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

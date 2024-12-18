package com.tsspringexperience.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="service_action")
@JsonIgnoreProperties
public class ServiceAction {  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id; 
	
	@ManyToOne
	@JoinColumn(name = "hotel_service_id", nullable = false)
	private HotelServices hotelService;

	@Column(name="action_name")
	private String actionName ;

	@Column(name="action_path")
	private String actionPath;

	@Column(name="enabled")
	private boolean enabled;  
	
	public ServiceAction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public HotelServices getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelServices service) {
		this.hotelService = service;
	}
	
}

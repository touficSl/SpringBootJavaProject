package com.tsspringexperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ammenities")
public class Ammenities {

	@JsonIgnore
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@JsonIgnore
	@Column(name="enabled")
	private boolean enabled;
	
	@JsonIgnore
	@Column(name="hotel_id")
	private String hotelId;
	
	@Column(name="icon")
	private String icon;
	
	@JsonIgnore
	@Column(name="icon_id")
	private int iconId;
	
	public Ammenities() {}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

}

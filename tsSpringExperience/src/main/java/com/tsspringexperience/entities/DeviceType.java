package com.tsspringexperience.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="request_device_type")
public class DeviceType {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="device_type_id")
	private String deviceTypeId;
	
	@Column(name="device_type_name")
	private String deviceTypeName; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	} 
}

package com.tsspringexperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_def_photo_gallery")
public class ImageEntity {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="enum_id")
	private String enumId;
	
	@Column(name="code")
	private String code;
	
	@Column(name="title")
	private String title;
	
	@Column(name="name")
	private String name;
	
	@Column(name="url")
	private String url;
	
	@Column(name="size")
	private String size;
	
	@Column(name="mimetype")
	private String mimetype;

	@Column(name="IS_PRIMARY")
	private Integer primary;
	
	public ImageEntity() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Integer getPrimary() {
		return primary;
	}

	public void setPrimary(Integer primary) {
		this.primary = primary;
	}
}

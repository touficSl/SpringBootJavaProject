package com.tsspringexperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_props")
public class EmailProps {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="host")
	private String host;
	
	@Column(name="port")
	private String port;
	
	@Column(name="auth")
	private boolean auth;
	
	@Column(name="starttls_enable")
	private boolean starttlsEnable;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="password")
	private String password;

	@Column(name="email_sender")
	private String emailSender;

	@Column(name="hotel_id")
	private String hotelId;

	@Column(name="is_default")
	private int isDefault;

	public EmailProps() {
		
	}

	public EmailProps(String host, String port, boolean auth, boolean starttlsEnable, String userEmail, String password,
			String emailSender, String hotelId) {
		super();
		this.host = host;
		this.port = port;
		this.auth = auth;
		this.starttlsEnable = starttlsEnable;
		this.userEmail = userEmail;
		this.password = password;
		this.emailSender = emailSender;
		this.hotelId = hotelId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
}

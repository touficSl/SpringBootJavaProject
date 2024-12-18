package com.tsspringexperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel")
public class Hotel {

	@Id
	@Column(name = "hotel_id")
	private String hotelId;

	@Column(name = "hotel_name")
	private String hotelName;
	
	@Column(name="hotel_policy")
	private String policy;
	
	@Column(name="hotel_privacy_policy")
	private String privacyPolicy;
	
	@Column(name="hotel_terms_and_conditions")
	private String termsAndConditions;
	
	@Column(name="hotel_address")
	private String hotelAddress;
	
	@Column(name="currency")
	private String currency;

	@Column(name = "dedge_price_model")
	private String dedgePriceModel;
	
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	public String getDedgePriceModel() {
		return dedgePriceModel;
	}
	
	public void setDedgePriceModel(String dedgePriceModel) {
		this.dedgePriceModel = dedgePriceModel;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getPrivacyPolicy() {
		return privacyPolicy;
	}
	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}

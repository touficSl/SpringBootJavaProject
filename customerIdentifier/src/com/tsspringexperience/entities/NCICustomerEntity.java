package com.tsspringexperience.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NCICustomerEntity {

	private int id;
	private String name;
	private String customerId;
	private String customerKey;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private String databaseCatalog;
	private String databaseDriver;
	private String privacyPolicy;
	private String termsAndConditions;
	private String timezone;
	private String firebaseServerKey;
	private String login;
	private String password;
	private boolean hasOtp;
	private boolean isDailyPointActive;
	private boolean isOtpResetPasswordActive;
	
	public NCICustomerEntity() {}
	
	public NCICustomerEntity(int id, String name, String customerId, String customerKey, String databaseUrl, String databaseUser, String databasePassword, String databaseCatalog, String databaseDriver, String privacyPolicy, String termsAndConditions, 
			String timezone, String firebaseServerKey, String login, String password, boolean hasOtp) {
		this.id = id;
		this.name = name;
		this.customerId = customerId;
		this.customerKey = customerKey;
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.databaseCatalog = databaseCatalog;
		this.databaseDriver = databaseDriver;
		this.privacyPolicy = privacyPolicy;
		this.termsAndConditions = termsAndConditions;
		this.timezone = timezone;
		this.firebaseServerKey = firebaseServerKey;
		this.login = login;
		this.password = password;
		this.hasOtp = hasOtp;
	}
	
	//another constructor with isDailyPointActive and isOtpResetPasswordActive
	public NCICustomerEntity(int id, String name, String customerId, String customerKey, String databaseUrl, String databaseUser, String databasePassword, String databaseCatalog, String databaseDriver, String privacyPolicy, String termsAndConditions, 
			String timezone, String firebaseServerKey, String login, String password, boolean hasOtp, boolean isDailyPointActive, boolean isOtpResetPasswordActive) {
		this.id = id;
		this.name = name;
		this.customerId = customerId;
		this.customerKey = customerKey;
		this.databaseUrl = databaseUrl;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
		this.databaseCatalog = databaseCatalog;
		this.databaseDriver = databaseDriver;
		this.privacyPolicy = privacyPolicy;
		this.termsAndConditions = termsAndConditions;
		this.timezone = timezone;
		this.firebaseServerKey = firebaseServerKey;
		this.login = login;
		this.password = password;
		this.hasOtp = hasOtp;
		this.isDailyPointActive = isDailyPointActive;
		this.isOtpResetPasswordActive = isOtpResetPasswordActive;
	}
	
	public NCICustomerEntity(ResultSet result) throws SQLException {
		this.setId(result.getInt("id"));
		this.setName(result.getString("name"));
		this.setCustomerId(result.getString("customer_id"));
		this.setCustomerKey(result.getString("customer_key"));
		this.setDatabaseUrl(result.getString("database_url"));
		this.setDatabaseUser(result.getString("database_user"));
		this.setDatabasePassword(result.getString("database_password"));
		this.setDatabaseCatalog(result.getString("database_catalog"));
		this.setDatabaseDriver(result.getString("database_driver"));
		this.setPrivacyPolicy(result.getString("privacy_policy"));
		this.setTermsAndConditions(result.getString("terms_and_conditions"));
		this.setTimezone(result.getString("timezone"));
		this.setFirebaseServerKey(result.getString("firebase_server_key"));
		this.setLogin(result.getString("login"));
		this.setPassword(result.getString("password"));
		this.setHasOtp(result.getBoolean("has_otp"));
		this.setIsDailyPointActive(result.getBoolean("is_dailypoint_active"));
		this.setIsOtpResetPasswordActive(result.getBoolean("is_otp_reset_password_active"));
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerKey() {
		return customerKey;
	}
	
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getDatabaseCatalog() {
		return databaseCatalog;
	}

	public void setDatabaseCatalog(String databaseCatalog) {
		this.databaseCatalog = databaseCatalog;
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
	
	public String toString() {
		String res =   "{\n"
					 + "	id : " + id + ", \n"
					 + "	name : '" + name + "', \n"
					 + "	customerId : '" + customerId + "', \n"
					 + "	databaseUrl : '" + databaseUrl + "', \n"
					 + "	databaseUser : '" + databaseUser + "', \n"
					 + "	databasePassword : '" + databasePassword + "', \n"
					 + "	databaseCatalog : '" + databaseCatalog + "', \n"
					 + "	privacyPolicy : '" + privacyPolicy + "', \n"
					 + "	termsAndConditions : '" + termsAndConditions + "', \n"
					 + "	timezone : '" + timezone + "', \n"
					 + "	firebaseServerKey : '" + firebaseServerKey + "', \n"
					 + "	login : '" + login + "', \n"
					 + "	password : '" + password + "' \n"
					 + "}";
		return res;
	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getFirebaseServerKey() {
		return firebaseServerKey;
	}

	public void setFirebaseServerKey(String firebaseServerKey) {
		this.firebaseServerKey = firebaseServerKey;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHasOtp(boolean hasOtp) {
		this.hasOtp = hasOtp;
	}
	
	public boolean isHasOtp() {
		return hasOtp;
	}
	
	public boolean getIsHasOtp() {
		return hasOtp;
	}
	
	public boolean getHasOtp() {
		return hasOtp;
	}

	public boolean getIsDailyPointActive() {
		return isDailyPointActive;
	}

	public void setIsDailyPointActive(boolean isDailyPointActive) {
		this.isDailyPointActive = isDailyPointActive;
	}

	public boolean getIsOtpResetPasswordActive() {
		return isOtpResetPasswordActive;
	}

	public void setIsOtpResetPasswordActive(boolean isOtpResetPasswordActive) {
		this.isOtpResetPasswordActive = isOtpResetPasswordActive;
	}

}

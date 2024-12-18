package com.tsspringexperience.utils;

public class SystemError {

	private String errorMessage;
	private String errorCode;
	private String userMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public SystemError() {
		super();
	}
	public SystemError(String errorMessage, String errorCode, String userMessage) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.userMessage = userMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
}

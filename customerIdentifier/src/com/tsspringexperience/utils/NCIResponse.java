package com.tsspringexperience.utils;

import java.util.ArrayList;

public class NCIResponse {
	
	private boolean success;
	private int statusCode;
	private String message;
	private String apiVersion;
	private Object data;
	private ArrayList<Object> errors;
	
	public NCIResponse(boolean success, int statusCode, String message, Object data) {
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
		this.apiVersion = Constants.JAR_VERSION;
		this.data = data;
		this.errors = new ArrayList<>();
	}
	
	public NCIResponse(boolean success, int statusCode, String message) {
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
		this.apiVersion = Constants.JAR_VERSION;
		this.data = new Object();;
		this.errors = new ArrayList<>();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setErrorMessage(String message) {
		this.message = message;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ArrayList<Object> getError() {
		return errors;
	}

	public void setError(ArrayList<Object> error) {
		this.errors = error;
	}

}

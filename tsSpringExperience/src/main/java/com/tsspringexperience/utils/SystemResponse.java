package com.tsspringexperience.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
public class SystemResponse {

	private String apiVersion;
	private boolean success;
	private Object data;
	private String message;
	private List<SystemError> errors;
	private int statusCode;
	public SystemResponse(String apiVersion, boolean success, Object data, String message, List<SystemError> errors, int statusCode) {
		super();
		this.apiVersion = apiVersion;
		this.success = success;
		this.data = data;
		this.message = message;
		this.errors = errors;
		this.statusCode = statusCode;
	}
	public SystemResponse() {
		super();
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SystemError> getErrors() {
		return errors;
	}
	public void setErrors(List<SystemError> errors) {
		this.errors = errors;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	} 
}

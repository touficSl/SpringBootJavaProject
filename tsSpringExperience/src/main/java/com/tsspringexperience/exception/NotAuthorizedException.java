package com.tsspringexperience.exception;

import java.util.List;

import com.tsspringexperience.multi.tenant.RequestContext;
import com.tsspringexperience.utils.SystemError;
import com.tsspringexperience.utils.SystemResponse;

public class NotAuthorizedException {
 
	private String apiVersion;
	private boolean success;
	private Object data;
	private String message = "Not authorized";
	private List<SystemError> errors;
	private int statusCode;
	

    public NotAuthorizedException() {
        super();
    } 

	public NotAuthorizedException(SystemResponse getJsonFormat) {
		super();
		RequestContext.clear();
		this.apiVersion = getJsonFormat.getApiVersion();
		this.success = getJsonFormat.isSuccess();
		this.data = getJsonFormat.getData();
		this.message = getJsonFormat.getMessage();
		this.errors = getJsonFormat.getErrors();
		this.statusCode = getJsonFormat.getStatusCode();
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
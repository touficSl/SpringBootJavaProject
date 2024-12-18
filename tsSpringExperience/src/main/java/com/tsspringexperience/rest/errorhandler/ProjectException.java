package com.tsspringexperience.rest.errorhandler;

@SuppressWarnings("serial")
public class ProjectException extends Exception{
	private String error;
	public ProjectException(String error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return error;
	}
}

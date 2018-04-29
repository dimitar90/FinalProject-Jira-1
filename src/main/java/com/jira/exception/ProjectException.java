package com.jira.exception;

public class ProjectException extends Exception {
private String wrongValue;
	
	public ProjectException() {
	}
	
	public ProjectException(String wrongValue) {
		super("Invalid project credentials: " + wrongValue);
	}
}
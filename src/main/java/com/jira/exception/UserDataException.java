package com.jira.exception;

public class UserDataException extends Exception{
	private String wrongValue;
	
	public UserDataException() {
	}
	
	public UserDataException(String wrongValue) {
		super("Invalid user credentials: " + wrongValue);
	}
}

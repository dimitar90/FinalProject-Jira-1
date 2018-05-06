package com.jira.exception;

public class UserDataException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6629257201565042022L;
	private String wrongValue;
	
	public UserDataException() {
	}
	
	public UserDataException(String wrongValue) {
		super("Invalid user credentials: " + wrongValue);
	}
	
}

package com.jira.model;

public enum ProjectTypeEnum {
	bussines("Business"),
	software("Software");
	
	private String value;
	 ProjectTypeEnum(final String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

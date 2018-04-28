package com.jira.model;

public enum TaskPriorityType {
	Low("Low"),
	Medium("Medium"),
	High("High"),
	Highest("Highest");
	
	private String value;

	TaskPriorityType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

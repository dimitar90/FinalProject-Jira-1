package com.jira.model;

public enum TaskStateType {
	toDo("To do"),
	inProgress("In progress"),
	done("Done"),
	codeReview("Code Review");
	
	private String value;

	TaskStateType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

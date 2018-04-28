package com.jira.model;

public enum TaskIssueType {
	newFeature("New Feature"),
	bug("Bug"),
	story("Story"),
	task("Task"),
	improvement("Improvement");
	
	private String value;

	TaskIssueType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

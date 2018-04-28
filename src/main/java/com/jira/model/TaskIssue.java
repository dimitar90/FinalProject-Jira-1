package com.jira.model;

public class TaskIssue {
	private int id;
	private TaskIssueType type;

	public TaskIssue(TaskIssueType type) {
		this.setType(type);
	}

	public TaskIssue(int id, TaskIssueType type) {
		this(type);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaskIssueType getType() {
		return type;
	}

	public void setType(TaskIssueType type) {
		this.type = type;
	}

}

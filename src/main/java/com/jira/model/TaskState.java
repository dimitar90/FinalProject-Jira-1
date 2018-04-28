package com.jira.model;

public class TaskState {
	private int id;
	private TaskStateType type;

	public TaskState(TaskStateType type) {
		this.setType(type);
	}

	public TaskState(int id, TaskStateType type) {
		this(type);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaskStateType getType() {
		return type;
	}

	public void setType(TaskStateType type) {
		this.type = type;
	}
}

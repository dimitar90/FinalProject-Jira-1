package com.jira.model;

public class TaskPriority {
	private int id;
	private TaskPriorityType type;
	
	public TaskPriority(TaskPriorityType type) {
		this.setType(type);
	}
	
	public TaskPriority(int id, TaskPriorityType type) {
		this(type);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaskPriorityType getType() {
		return type;
	}

	public void setType(TaskPriorityType type) {
		this.type = type;
	}
}

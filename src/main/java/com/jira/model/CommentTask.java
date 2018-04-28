package com.jira.model;

import java.time.LocalDateTime;

public class CommentTask {
	private int id;
	private String description;
	private LocalDateTime dateTime;
	private int userId;
	private int taskId;

	public CommentTask(String description, LocalDateTime dateTime, int userId, int taskId) {
		this.setDescription(description);
		this.setDateTime(dateTime);
		this.setUserId(userId);
		this.setTaskId(taskId);
	}
	
	public CommentTask(int id, String description, LocalDateTime dateTime, int userId, int taskId) {
		this(description, dateTime, userId, taskId);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}

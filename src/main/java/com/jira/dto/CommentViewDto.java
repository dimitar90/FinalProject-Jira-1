package com.jira.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.jira.model.User;

public class CommentViewDto {
	private String description;
	private LocalDateTime dateTime;
	private User user;

	public CommentViewDto(String description, LocalDateTime dateTime, User user) {
		this.setDescription(description);
		this.setDateTime(dateTime);
		this.setUser(user);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

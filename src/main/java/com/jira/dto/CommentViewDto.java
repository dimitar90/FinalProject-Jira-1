package com.jira.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.jira.model.User;

public class CommentViewDto {
	private String description;
	private LocalDateTime dateTime;
	private String username;
	//private String userImageBase64;
	private String userAvatarName;

	public CommentViewDto(String description, LocalDateTime dateTime, String username, String userAvatarName) {
		this.setDescription(description);
		this.setDateTime(dateTime);
		this.setUsername(username);
		//this.setUserImageBase64(userImageBase64);
		this.setUserAvatarName(userAvatarName);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
//	public String getUserImageBase64() {
//		return userImageBase64;
//	}
//
//	public void setUserImageBase64(String userImageBase64) {
//		this.userImageBase64 = userImageBase64;
//	}
	
	public String getUserAvatarName() {
		return userAvatarName;
	}

	public void setUserAvatarName(String userAvatarName) {
		this.userAvatarName = userAvatarName;
	}
}

package com.jira.dto;

public class UserDto {
	private int id;
	private String email;
	private String name;
	private String imageUrl;

	public UserDto(int id, String email, String name, String imageUrl) {
		this.setId(id);
		this.setEmail(email);
		this.setName(name);
		this.setImageUrl(imageUrl);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static UserDto getDto(int id, String email, String name, String imageUrl) {
		return new UserDto(id, email, name, imageUrl);
	}
}

package com.jira.model;

public class User {
	private String name;
	private String email;
	private String password;
	private String imageUrl;
	private int id;

	public User(int id, String name, String email, String password, String imageUrl) {
		this(name, email, password, imageUrl);
		this.id = id;
	}

	public User(String name, String email, String password, String imageUrl) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.imageUrl = imageUrl;
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(int id, String name, String email, String imageUrl) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
	}

	public static User getUser(String name, String email, String password) {
		return new User(name, email, password);
	}

	public static User getUser(String name, String email, String password, String imageUrl) {
		return new User(name, email, password, imageUrl);
	}

	public static User getUser(int id, String name, String email, String imageUrl) {
		return new User(id, name, email, imageUrl);
	}

	public static User getUser(int id, String name, String password, String email, String imageUrl) {
		return new User(id, name, email, password, imageUrl);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void changeImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setIt(int id) {
		this.id = id;
	}
}
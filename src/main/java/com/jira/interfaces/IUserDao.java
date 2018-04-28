package com.jira.interfaces;

import java.util.Collection;

import com.jira.model.User;

public interface IUserDao {
	public User createUser(String username, String email, String password, String imageUrl);

	public User getUser(String email, String password) throws Exception;

	public void saveUser(User u) throws Exception;

	public Collection<User> getAll() throws Exception;

	public void changeName(String newName, int userId) throws Exception;

	public void changePassword(String newPass, int userId) throws Exception;

	public int getUserId(String email, String password) throws Exception;

	public User getUserById(int id) throws Exception;

	public User createUser(String username, String email, String password);

	public void changeImageUrl(String imageUrl, User u) throws Exception;
	
	
}

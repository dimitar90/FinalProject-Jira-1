package com.jira.interfaces;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.jira.dto.UserDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.model.User;

public interface IUserManager {

	public User register(String username, String email, String password, String confirmPassword, String imageUrl)
			throws Exception;

	public boolean isEmailExist(String email) throws Exception;

	public boolean checkUsername(String username) throws Exception;

	public User getLoggedUser(HttpSession session) throws Exception;

	public boolean checkPassword(String password) throws Exception;

	public boolean checkEmail(String email) throws Exception;

	public void comparePassword(String password, String confirmPassword) throws Exception;

	public User login(String email, String password) throws Exception;

	public String saveImageUrl(MultipartFile file, String email) throws Exception;

	public String changeImageUrl(MultipartFile filePart, String email) throws Exception;

	public User changeData(String newName, String newPass, String email, String oldPass) throws Exception;

	public boolean isValidParam(String parm) throws Exception;

	public void takeData(String imageUrl, User u) throws Exception;

	public UserDto getUserDtoById(Integer id) throws Exception;

	public UserDto getUserDtoByProjectId(int projectId) throws Exception;

	public User getUserById(int userId) throws UserDataException, Exception;

	public Optional<User> findUserByEmail(String email) throws Exception;

	public Optional<User> findUserByResetToken(String resetToken) throws Exception;;
	
	public void saveResetTokenDB(User user) throws Exception;
	
	public void resetPasswordByToken(User user) throws Exception;
	
	public void resetTheTokenByEmail(User user) throws Exception;
	


}

package com.jira.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jira.dao.UserDao;
import com.jira.dto.UserDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IUserManager;
import com.jira.model.User;

@Component
public class UserManager implements IUserManager {
	private static final int MIN_LENGTH_PASSWORD = 4;
	private static final String MSG_IVNALID_CREDENTIALS = "Invalid username or password";
	private static final String MSG_INVALID_EMAIL = "email missmatch";
	private static final int MIN_FULL_NAME_LENGTH = 3;
	private static final String MSG_INVALID_PASSWORD = "Invalid username or password";
	private static final String MSG_NO_IMAGE_UPLOADED = "Image required";
	public static final String PATH = "D:\\images\\Users\\";
	public static final String EXTENTION = "-pic.jpg";

	@Autowired
	private UserDao userDao;

	@Override
	public User register(String username, String email, String password, String confirmPassword, String imageUrl)
			throws UserDataException, SQLException, DatabaseException {

		User u = this.userDao.createUser(username, email, password, imageUrl);
		this.userDao.saveUser(u);

		return u;
	}

	@Override
	public boolean isEmailExist(String email) throws SQLException, UserDataException {
		return this.userDao.chechEmail(email);
	}

	@Override
	public boolean checkUsername(String username) {
		return ((username.isEmpty()) || (!username.matches("^[_A-z0-9]*((-|\\s)*[_A-z0-9])*$")
				|| (username.length() < MIN_FULL_NAME_LENGTH)));

	}

	@Override
	public boolean checkEmail(String email) {
		return (email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\", Pattern.CASE_INSENSITIVE"));
	}

	@Override
	public User getLoggedUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

	@Override
	public boolean checkPassword(String password) {
		return (password.isEmpty() || password.length() < MIN_LENGTH_PASSWORD);
	}

	@Override
	public void comparePassword(String password, String confirmPassword) throws UserDataException {
		if (password.compareTo(confirmPassword) != 0) {
			throw new UserDataException(MSG_IVNALID_CREDENTIALS);
		}
	}

	public boolean comparingPassword(String passowrd, String confirmPassword) {
		return passowrd.compareTo(confirmPassword) != 0;
	}

	@Override
	public User login(String email, String password) throws UserDataException, SQLException {
		User u = this.userDao.getUser(email, password);
		return u;
	}

	@Override
	public String saveImageUrl(MultipartFile file, String email) throws IOException, UserDataException {
		String imgUrl = PATH + email + EXTENTION;

		if (!file.isEmpty()) {
			File f = new File(imgUrl);
			if (!f.exists()) {
				f.createNewFile();
			}
			InputStream is = file.getInputStream();

			BufferedInputStream bufferIn = new BufferedInputStream(is);
			BufferedOutputStream buffOut = new BufferedOutputStream(new FileOutputStream(f));

			byte[] buffer = new byte[1024];

			while (bufferIn.read(buffer) != -1) {
				buffOut.write(buffer);
			}

			bufferIn.close();
			buffOut.close();

			return imgUrl;
		}
		throw new UserDataException(MSG_NO_IMAGE_UPLOADED);

	}

	@Override
	public String changeImageUrl(MultipartFile filePart, String email) throws IOException {
		File dir = new File(PATH);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().endsWith(email + EXTENTION)) {
					child.delete();
					break;
				}
			}
		}
		String imgUrl = PATH + email + EXTENTION;
		File f = new File(imgUrl);
		if (!f.exists()) {
			f.createNewFile();
		}

		InputStream is = filePart.getInputStream();

		BufferedInputStream bufferIn = new BufferedInputStream(is);
		BufferedOutputStream buffOut = new BufferedOutputStream(new FileOutputStream(f));

		byte[] buffer = new byte[1024];

		while (bufferIn.read(buffer) != -1) {
			buffOut.write(buffer);
		}

		bufferIn.close();
		buffOut.close();
		return imgUrl;
	}

	@Override
	public User changeData(String newName, String newPass, String email, String oldPass) throws Exception {
		// check what u have to change
		int userId = this.userDao.getUserId(email, oldPass);

		if (userId == 0) {
			return null;
		}

		return this.userDao.getUserById(userId);
	}

	@Override
	public boolean isValidParam(String parm) {
		if (parm.isEmpty() || parm.length() < MIN_FULL_NAME_LENGTH) {
			return false;
		}
		return true;
	}

	@Override
	public void takeData(String imageUrl, User u) throws UserDataException, Exception {
		checkEmail(u.getEmail());

		this.userDao.changeImageUrl(imageUrl, u);
	}

	@Override
	public UserDto getUserDtoById(Integer id) throws DatabaseException {

		return this.userDao.getUserDtoById(id);
	}

	@Override
	public UserDto getUserDtoByProjectId(int projectId) throws UserDataException, DatabaseException {
		return userDao.getDtoByProjectId(projectId);
	}

	@Override
	public User getUserById(int userId) throws UserDataException, SQLException {
		return userDao.getUserById(userId);
	}

	@Override
	public Optional<User> findUserByEmail(String email) throws DatabaseException {
		return userDao.findByEmail(email);
	}

	@Override
	public Optional<User> findUserByResetToken(String resetToken) throws DatabaseException {
		return userDao.findByResetToken(resetToken);
	}

	@Override
	public void saveResetTokenDB(User user) throws DatabaseException {
		this.userDao.saveToken(user);
	}

	@Override
	public void resetPasswordByToken(User user) throws DatabaseException {
		this.userDao.resetPassword(user);
	}

	@Override
	public void resetTheTokenByEmail(User user) throws DatabaseException {
		userDao.resetToken(user);
	}

	public String checkCredentials(String username, String password, String confirmPassword, String email)
			throws SQLException, UserDataException {
		String message = null;
		if (this.checkUsername(username)) {
			message = "Invalid username for register";
			return message;
		}

		if (this.comparingPassword(password, confirmPassword)) {
			message = "the password must be the same length";
			return message;
		}
		if (this.checkPassword(password)) {
			message = "Password should be minimum 4 characters";
			return message;
		}

		if (this.checkEmail(email)) {
			message = "Invalide symbols for email";
			return message;
		}

		if (this.isEmailExist(email)) {
			message = "The email already exist";
			return message;
		}
		return message;
	}
}

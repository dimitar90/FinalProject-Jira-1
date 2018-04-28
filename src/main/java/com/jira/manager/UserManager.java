package com.jira.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.dao.UserDao;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.model.User;

@Component
public class UserManager {
	private static final int MIN_LENGTH_PASSWORD = 4;
	private static final int MIN_LENGTH_EMAIL = 6;
	private static final String MSG_INVALID_USERNAME = "Invalid username or password";
	private static final String MSG_IVNALID_PASSWORD = "Invalid username or password";
	private static final String MSG_INVALID_EMAIL = "email missmatch";
	private static final int MIN_NAME_LENGTH = 2;
	private static final String MSG_INVALID_PASSWORD = "Invalid username or password";
	
	@Autowired
	private UserDao userDao;

	public User register(String username, String email, String password, String confirmPassword, String imageUrl)
			throws UserDataException, SQLException, DatabaseException {
		// validate data
		checkUsername(username);

		comparePassword(password, confirmPassword);

		checkPassword(password);

		checkEmail(email);
		// create user
		
		User u = this.userDao.createUser(username, email, password, imageUrl);
		this.userDao.saveUser(u);
		
		return u;
	}

	public void checkUsername(String username) throws UserDataException {
		if (username.isEmpty() || username.length() < MIN_NAME_LENGTH) {
			throw new UserDataException(MSG_INVALID_USERNAME);
		}
	}

	public void checkEmail(String email) throws UserDataException {
		if (email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\", Pattern.CASE_INSENSITIVE")
				|| email.length() < MIN_LENGTH_EMAIL) {
			throw new UserDataException(MSG_INVALID_EMAIL);
		}
	}

	public void checkPassword(String password) throws UserDataException {
		if (password.isEmpty() || password.length() < MIN_LENGTH_PASSWORD) {
			throw new UserDataException(MSG_INVALID_PASSWORD);
		}
	}

	public void comparePassword(String password, String confirmPassword) throws UserDataException {
		if (password.compareTo(confirmPassword) != 0) {
			throw new UserDataException(MSG_IVNALID_PASSWORD);
		}
	}

	public User login(String email, String password) throws UserDataException, SQLException {
		User u = this.userDao.getUser(email, password);
		return u;
	}

	public String saveImageUrl(Part filePart, String email) throws IOException {

		String imgUrl = "D:\\Users\\" + email + "-pic.jpg";

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

	public String changeImageUrl(Part filePart, String email) throws IOException {
		File dir = new File("D:\\Users");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().endsWith(email + "-pic.jpg")) {
					child.delete();
					break;
				}
			}
		}
		String imgUrl = "D:\\Users\\" + email + "-pic.jpg";
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

	public User changeData(String newName, String newPass, String email, String oldPass) throws Exception {
		// check what u have to change
		int userId = this.userDao.getUserId(email, oldPass);

		if (isValidParam(newName)) {
			this.userDao.changeName(newName, userId);
		}

		if (isValidParam(newPass)) {
			this.userDao.changePassword(newPass, userId);
		}

		return this.userDao.getUserById(userId);
	}

	private boolean isValidParam(String parm) {
		if (parm.isEmpty() || parm.length() < MIN_NAME_LENGTH) {
			return false;
		}
		return true;
	}

	public void takeData(String imageUrl, User u) throws UserDataException, Exception {
		checkEmail(u.getEmail());

		this.userDao.changeImageUrl(imageUrl, u);
	}

}

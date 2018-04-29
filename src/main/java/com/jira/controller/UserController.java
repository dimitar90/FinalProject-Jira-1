package com.jira.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.exception.UserDataException;
import com.jira.manager.UserManager;
import com.jira.model.User;

@Controller
public class UserController {
	private static final int EXP_TIME = 2 * 60;
	private static final String MSG_INVALID_USER_NAME_OR_PASSWORD = "Ivalid username or password";

	@Autowired
	private UserManager manager;

	// register methods
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ServletRequest request, HttpSession s, @RequestParam("singleFile") MultipartFile singleFile,
			@RequestParam String username, @RequestParam String email, @RequestParam String password,
			@RequestParam String confirmPassword) {
		if (!singleFile.isEmpty()) {

		}
		try {

			String imageUrl = this.manager.saveImageUrl(singleFile, email);

			User user = this.manager.register(username, email, password, confirmPassword, imageUrl);

			s.setAttribute("user", user);

			s.setMaxInactiveInterval(EXP_TIME);

			return "main";

		} catch (UserDataException e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			return "error";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			return "error";
		}

	}

	// login methods
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession s, @RequestParam String email,
			@RequestParam String password) {
		try {
			User user = this.manager.login(email, password);

			if (user != null) {
				s.setAttribute("user", user);
				return "main";
			}
			throw new UserDataException(MSG_INVALID_USER_NAME_OR_PASSWORD);

		} catch (UserDataException e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			return "error";
		}
	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession s) {

		s.invalidate();

		return "index";

	}

	// edit
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile() {

		return "edit-profile";

	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(HttpSession s,HttpServletRequest req,@RequestParam String email,@RequestParam String oldPass,@RequestParam String oldConfPass,
			@RequestParam String newName,@RequestParam String newPass) {
		// TODO check session
		try {
//		String email = req.getParameter("email");
//		String oldPass = req.getParameter("old pass");
//		String oldConfPass = req.getParameter("old conf pass");
//
//		String newName = req.getParameter("new name");
//		String newPass = req.getParameter("new pass");
		// check old password and the email
		manager.comparePassword(oldPass, oldConfPass);
		manager.checkPassword(oldPass);
		manager.checkEmail(email);

		// change data
		User user = manager.changeData(newName, newPass, email, oldPass);
		
		s.setAttribute("user", user);
//		req.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(req, resp);
		return "main";
	} catch (UserDataException e) {
		e.printStackTrace();
//		req.getRequestDispatcher("error.jsp").forward(req, resp);
		return "error";
	} catch (Exception e) {
		e.printStackTrace();
		req.setAttribute("exception", e);
		return "error";
	}
}
	

	// shown pic
	@RequestMapping(value = "/getPic", method = RequestMethod.GET)
	public void getImage(HttpSession s, HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		User u = (User) s.getAttribute("user");
		try {
			File f = new File(UserManager.PATH + u.getEmail() + UserManager.EXTENTION);
			if (!f.exists()) {
				f.createNewFile();
			}
			InputStream is = new FileInputStream(f);
			OutputStream os = resp.getOutputStream();
			int b = is.read();
			while (b != -1) {
				os.write(b);
				b = is.read();
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			request.getRequestDispatcher("error").forward(request, resp);
		}
	}
}

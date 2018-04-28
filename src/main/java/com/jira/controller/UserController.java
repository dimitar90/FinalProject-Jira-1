package com.jira.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String register(HttpServletRequest request) {
		try {
			Part filePart = request.getPart("create");

			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm password");
			String imageUrl = this.manager.saveImageUrl(filePart, email);

			User user = this.manager.register(username, email, password, confirmPassword, imageUrl);

			HttpSession session = request.getSession();

			session.setAttribute("user", user);

			session.setMaxInactiveInterval(EXP_TIME);

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
	public String login(HttpServletRequest request) {
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			User user = this.manager.login(email, password);

			if (user != null) {
				request.getSession().setAttribute("user", user);
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

	//logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		request.getSession().invalidate();
		
		return "index";
		
	}
}

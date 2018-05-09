package com.jira.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.dao.ProjectCategoryDao;
import com.jira.dao.ProjectDao;
import com.jira.dao.ProjectTypeDao;
import com.jira.dto.ProjectDto;
import com.jira.dto.UserDto;
import com.jira.exception.UserDataException;
import com.jira.manager.UserManager;
import com.jira.model.User;
import com.jira.utils.EmailUtil;

@Controller
public class UserController {
	private static final int EXP_TIME = 10 * 60;

	@Autowired
	private EmailUtil emailSender;

	@Autowired
	private UserManager userManager;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectCategoryDao projectCategoryDao;

	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model) {
		try {
		Map<String,Integer> categories = projectCategoryDao.getCategoriesForChart();
		
		return "projects-chart";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, HttpServletRequest request,
			@RequestParam("singleFile") MultipartFile singleFile,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "confirmPassword", required = true) String confirmPassword) {
		try {

			String imageUrl = this.userManager.saveImageUrl(singleFile, email);
			HttpSession session = request.getSession();

			if (this.userManager.getLoggedUser(session) != null) {
				request.setAttribute("errorMsg", "You are already logged in!");
				return "register";
			}

			String errorMsg = this.userManager.checkCredentials(username, password, confirmPassword, email);

			if (errorMsg != null) {
				request.setAttribute("errorMsg", errorMsg);
				return "register";
			}

			User user = this.userManager.register(username, email, password, confirmPassword, imageUrl);

			session.setAttribute("user", user);
			session.setMaxInactiveInterval(EXP_TIME);

			List<ProjectDto> dtoList = projectDao.getAllBelongingToUser(user.getId());
			session.setAttribute("user", user);

			if (dtoList.isEmpty()) {
				return "main";
			}

			session.setAttribute("myProjects", dtoList);
			return "main";

		} catch (UserDataException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.invalidate();
		return "index";
	}

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String getLoginPage() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, @RequestParam String email,
			@RequestParam String password) {
		try {
			HttpSession session = request.getSession();

			User user = this.userManager.login(email, password);
			if (user == null) {
				request.setAttribute("errorMsg", "Invalide username or password");
				return "index";
			}
			session.removeAttribute("myProjects");

			List<ProjectDto> dtoList = projectDao.getAllBelongingToUser(user.getId());
			if (!dtoList.isEmpty()) {
				user.increaseCreatorProjectCount();
			}

			session.setAttribute("user", user);
			session.setMaxInactiveInterval(EXP_TIME);

			if (dtoList.isEmpty()) {
				return "main";
			}

			session.setAttribute("myProjects", dtoList);

			return "main";
		} catch (UserDataException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			model.addAttribute("error", e);
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			model.addAttribute("error", e);
			return "error";
		}
	}

	// edit
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile(HttpSession session) {
		if (this.userManager.getLoggedUser(session) == null) {
			return "index";
		}
		return "edit-profile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(HttpSession session, HttpServletRequest request, Model model, @RequestParam String email,
			@RequestParam String oldPass, @RequestParam String oldConfPass, @RequestParam String newName,
			@RequestParam String newPass) {
		try {

			if (this.userManager.checkUsername(newName)) {
				request.setAttribute("errorMsg", "Invalid username for edit profile");
				return "edit-profile";
			}

			if (this.userManager.comparingPassword(oldPass, oldConfPass)) {
				request.setAttribute("errorMsg", "password not the same");
				return "edit-profile";
			}

			if (this.userManager.checkPassword(oldPass)) {
				request.setAttribute("errorMsg", "Password should be minimum 4 characters");
				return "edit-profile";
			}

			if (this.userManager.checkEmail(email)) {
				request.setAttribute("errorMsg", "Invalide symbols for email");
				return "edit-profile";
			}

			if (this.userManager.checkUsername(newName)) {
				request.setAttribute("errorMsg", "Invalid new username");
				return "edit-profile";
			}

			User user = userManager.changeData(newName, newPass, email, oldPass);

			if (user == null) {
				request.setAttribute("errorMsg", "Invalid user data");
				return "edit-profile";
			}

			session.setAttribute("user", user);
			return "main";
		} catch (UserDataException e) {
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			model.addAttribute("error", e);
			return "error";
		}
	}

	// shown pic
	@RequestMapping(value = "/getPicSession", method = RequestMethod.GET)
	public void getImageUser(Model model, HttpSession s, HttpServletRequest request, HttpServletResponse resp)
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
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			request.getRequestDispatcher("error").forward(request, resp);
		}
	}

	@RequestMapping(value = "/getPicLead", method = RequestMethod.GET)
	public void getImageLead(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		UserDto dto = (UserDto) session.getAttribute("dto");

		try {
			File f = new File(UserManager.PATH + dto.getEmail() + UserManager.EXTENTION);
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

			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", e);
			request.getRequestDispatcher("error").forward(request, resp);
		}
	}

	@RequestMapping(value = "/changePic", method = RequestMethod.POST)
	public String changeProfilePic(Model model, HttpServletRequest request,
			@RequestParam("fileForChange") MultipartFile singleFile) {
		try {

			if (singleFile.isEmpty()) {
				return "edit-profile";
			}
			User u = (User) request.getSession().getAttribute("user");
			String imageUrl = userManager.changeImageUrl(singleFile, u.getEmail());

			userManager.takeData(imageUrl, u);
			return "edit-profile";
		} catch (UserDataException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			model.addAttribute("error", e);
			return "error";
		}

	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public String getMyProfileView(HttpSession session, Model model) {
		try {
			User user = (User) session.getAttribute("user");

			if (user == null) {
				return "index";
			}
			UserDto dto = userManager.getUserDtoById(user.getId());

			int projectsCount = this.projectDao.getProjectsCount(user.getId());

			user.setCreatorProjectsCount(projectsCount);
			// session.setAttribute("dto", dto);

			return "my-profile";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e);
			return "error";
		}

	}

	@RequestMapping(value = "/userId/{id}", method = RequestMethod.GET)
	public String viewLead(Model model, @PathVariable int id, HttpSession session) {
		try {

			UserDto dto = userManager.getUserDtoByProjectId(id);
			int projectsCount = this.projectDao.getProjectsCount(dto.getId());
			dto.setDtoProjectsCount(projectsCount);
			model.addAttribute("dto", dto);
			session.setAttribute("dto", dto);
			return "lead";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e);
			return "error";
		}

	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String showForgotPassPage(HttpSession session) {
		if (userManager.getLoggedUser(session) != null) {
			return "main";
		}
		return "forgotPassword";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String submitForgotPassword(@RequestParam(value = "email", required = true) String userEmail,
			HttpSession session, Model model) {
		try {

			Optional<User> optional = userManager.findUserByEmail(userEmail);

			// if no user -> msg no email
			if (!optional.isPresent()) {
				model.addAttribute("errorMsg", "We didn't find an account for that e-mail address.");
			} else {

				// if there is a user -> generate reset token and set to him
				User user = optional.get();
				String token = UUID.randomUUID().toString().replaceAll("-", "");
				user.setResetToken(token);

				this.userManager.saveResetTokenDB(user);

				this.emailSender.sendEmail(userEmail, EmailUtil.SUBJECT_TEXT_FORGOTTEN_PASSWORD,
						String.format(EmailUtil.FORGOTTEN_PASSWORD_EMAIL_TEXT, user.getName(), user.getResetToken()));
				return "forgotPassword";
			}

			return "forgotPassword";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e);
			return "error";
		}
	}

	@RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET)
	public String showResetPasswordPage(Model model, @PathVariable("token") String token) {
		model.addAttribute("token", token);
		return "resetPassword";
	}

	@RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.POST)
	public String resetPassword(Model model,HttpServletRequest request, @PathVariable("token") String token) {
		try {
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			// check if user has token and if passwords are compared each other
			Optional<User> optional = userManager.findUserByResetToken(token);
			
			if (!optional.isPresent() || userManager.comparingPassword(password, confirmPassword)) {
				model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
				return "resetPassword";
			} else {
				// if yes -> change the password and delete the token from db
				User user = optional.get();

				user.setPassword(password);
				user.setResetToken(token);

				this.userManager.resetPasswordByToken(user);
				this.userManager.resetTheTokenByEmail(user);
			}

			return "redirect:../";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e);
			return "error";
		}
	}

}

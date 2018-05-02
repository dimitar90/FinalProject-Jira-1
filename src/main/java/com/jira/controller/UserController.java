package com.jira.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.manager.UserManager;
import com.jira.model.User;

@Controller
public class UserController {
	private static final int EXP_TIME = 2 * 60;
	private static final String MSG_INVALID_USER_NAME_OR_PASSWORD = "Ivalid username or password";

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
	
	// register methods
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, HttpServletRequest request, @RequestParam("singleFile") MultipartFile singleFile,
			@RequestParam String username, @RequestParam String email, @RequestParam String password,
			@RequestParam String confirmPassword) {
		try {
			String imageUrl = this.userManager.saveImageUrl(singleFile, email);
			HttpSession s = request.getSession();
			User user = this.userManager.register(username, email, password, confirmPassword, imageUrl);

			s.setAttribute("user", user);
			s.setMaxInactiveInterval(EXP_TIME);

			List<ProjectDto> dtoList = projectDao.getAllBelongingToUser(user.getId());
			s.setAttribute("user", user);
			if (dtoList.isEmpty()) {
				return "main";	
			}
			
			s.setAttribute("myProjects", dtoList);
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


	
	@RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
	public String getLoginPage() {
			return "index";
		}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, @RequestParam String email, @RequestParam String password) {
		try {
			HttpSession s = request.getSession();
			User user = this.userManager.login(email, password);
			s.removeAttribute("myProjects");
			List<ProjectDto> dtoList = projectDao.getAllBelongingToUser(user.getId());
			s.setAttribute("user", user);
			if (dtoList.isEmpty()) {
				return "main";
			}
			s.setAttribute("myProjects", dtoList);
			
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
	public String editProfile(HttpSession s, Model model, @RequestParam String email, @RequestParam String oldPass,
			@RequestParam String oldConfPass, @RequestParam String newName, @RequestParam String newPass) {
		// TODO check session
		try {
			userManager.comparePassword(oldPass, oldConfPass);
			userManager.checkPassword(oldPass);
			userManager.checkEmail(email);

			// change data
			User user = userManager.changeData(newName, newPass, email, oldPass);

			s.setAttribute("user", user);
			return "main";
		} catch (UserDataException e) {
			e.printStackTrace();
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	// shown pic
	@RequestMapping(value = "/getPicSession", method = RequestMethod.GET)
	public void getImageUser(HttpSession s, HttpServletRequest request, HttpServletResponse resp)
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
	
	@RequestMapping(value = "/changePic", method = RequestMethod.POST)
	public String changeProfilePic(Model model, HttpServletRequest request, @RequestParam("fileForChange") MultipartFile singleFile) {
		try {
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
			return "error";
		}
		

	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public String getMyProfileView(HttpSession s) {
		try {
			User u = (User) s.getAttribute("user");

			UserDto dto = userManager.getUserDtoById(u.getId());
			s.setAttribute("dto", dto);

			return "my-profile";
		} catch (DatabaseException e) {
			e.printStackTrace();
			return "error";
		}

	}
	

	@RequestMapping(value = "/leadId/{id}", method = RequestMethod.GET)
	public String viewLead(Model model, @PathVariable int id, HttpSession s) {
		try {
			UserDto dto = userManager.getUserDtoById(id);
			model.addAttribute("dto", dto);
			//get and project tasks here
			
			return "lead";
		} catch (DatabaseException e) {
			e.printStackTrace();
			return "error";
		}

	}

}

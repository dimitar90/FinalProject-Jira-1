package com.jira.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.dao.ProjectCategoryDao;
import com.jira.dao.ProjectDao;
import com.jira.dao.ProjectTypeDao;
import com.jira.dao.TaskDao;
import com.jira.dto.ProjectDto;
import com.jira.dto.ProjectTypeBusinessDto;
import com.jira.dto.ProjectTypeSoftwareDto;
import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.UserDto;
import com.jira.exception.DatabaseException;
import com.jira.manager.UserManager;
import com.jira.model.Project;
import com.jira.model.ProjectCategory;
import com.jira.model.ProjectType;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
	private static final int RECORDS_PER_PAGE = 3;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Autowired
	private ProjectCategoryDao projectCategoryDao;

	@Autowired
	private UserManager userManager;

	@Autowired
	private TaskDao taskDao;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		try {// TODO session validate
				// get all project, categories and types
			List<ProjectCategory> projectCategories = projectCategoryDao.getAllCategories();
			List<ProjectType> projectTypes = projectTypeDao.getAllProjectTypes();
			// put them into the request
			model.addAttribute("projectCategories", projectCategories);
			model.addAttribute("projectTypes", projectTypes);

			return "create-project";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, @RequestParam String projectName, @RequestParam String projectType,
			@RequestParam String projectCategory, HttpSession session) throws ServletException, IOException {
		try {
			projectDao.isValidProjectName(projectName);
			int projTypeId = Integer.parseInt(projectType);
			int projCategoryId = Integer.parseInt(projectCategory);
			User user = (User) session.getAttribute("user");

			Project p = projectDao.getProject(projectName, projCategoryId, projTypeId, user.getId());

			projectDao.saveProject(p);
			return "redirect:./all/0";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/userProjects/{currPage}", method = RequestMethod.GET)
	public String showAllProjects(Model model, HttpSession session, @PathVariable int currPage) {
		List<ProjectDto> userProjects = new ArrayList<>();

		// TODO you can check the view of project, only if have a session

		// int userId = ((User) session.getAttribute("user")).getId();

		int userId = ((UserDto) session.getAttribute("dto")).getId();
		session.removeAttribute("dto");
		try {
			int projectCount = this.projectDao.getCountOfUserProjects(userId);
			int noOfPages = (projectCount / RECORDS_PER_PAGE) - 1;

			if (projectCount % RECORDS_PER_PAGE != 0) {
				noOfPages++;
			}

			model.addAttribute("currRecordPage", RECORDS_PER_PAGE);
			model.addAttribute("projectCount", projectCount);
			model.addAttribute("noOfPages", noOfPages);
			model.addAttribute("currentPage", currPage);

			List<ProjectDto> projects = this.projectDao.getProjectPerPageAndUserId(userId, currPage, RECORDS_PER_PAGE);

			// userProjects.addAll(projectDao.getAllBelongingToUser(userId));
			User user = userManager.getUserById(userId);
			model.addAttribute("userProjects", projects);
			model.addAttribute("userName", user.getName());
			return "user-projects-view";
		} catch (DatabaseException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		} catch (Exception e) {
			model.addAttribute("exception", e);
			return "error";
		}

	}

	@RequestMapping(value = "/showAllSoftware", method = RequestMethod.GET)
	public String showAllSoftware(Model model) {
		try {
			List<ProjectTypeSoftwareDto> softwareProjects = new ArrayList<>();
			softwareProjects.addAll(projectTypeDao.getAllSoftwareProjects());

			model.addAttribute("softwareProjects", softwareProjects);
			return "show-software-projects";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}

	}

	@RequestMapping(value = "/showAllBusiness", method = RequestMethod.GET)
	public String showAllBusiness(Model model) {
		try {
			List<ProjectTypeBusinessDto> businessProjects = new ArrayList<>();
			businessProjects.addAll(projectTypeDao.getAllBusinessProjects());

			model.addAttribute("businessProjects", businessProjects);
			return "show-business-projects";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}

	}

	@RequestMapping(value = "/projectId/{id}", method = RequestMethod.GET)
	public String viewProject(Model model, @PathVariable int id) {
		try {
			ProjectDto dtoProject = projectDao.getProjectDtoById(id);

			model.addAttribute("dtoProject", dtoProject);

			List<TaskBasicViewDto> tasksDto = taskDao.getAllByProjectId(id);

			model.addAttribute("tasksDto", tasksDto);
			return "project-view";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/delete/{dtoProjectId}", method = RequestMethod.GET)
	public String deletProject(Model model, @PathVariable("dtoProjectId") int projectId, HttpServletRequest request) {
		try {
			if (!this.projectDao.isExistById(projectId)) {
				return "redirect:../all/0";
			}

			Project project = this.projectDao.getById(projectId);

			if (project == null) {
				return "redirect:../all/0";
			}

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			int loggedUserId = user.getId();
			int leadId = projectDao.getLeadByProjectId(projectId);

			if (leadId != loggedUserId) {
				return "redirect:../showAllProjects";
			}

			projectDao.deleteProjectById(projectId);
			session.removeAttribute("myProjects");
			List<ProjectDto> dtoList = projectDao.getAllBelongingToUser(user.getId());

			session.setAttribute("myProjects", dtoList);
			return "project-view";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/{currPage}")
	public String showAllProjectPages(Model model, @PathVariable Integer currPage) {
		// TODO validation for logged user

		try {
			int projectCount = this.projectDao.getCount();
			int noOfPages = (projectCount / RECORDS_PER_PAGE) - 1;
			if (projectCount % RECORDS_PER_PAGE != 0) {
				noOfPages++;
			}

			model.addAttribute("currRecordPage", RECORDS_PER_PAGE);
			model.addAttribute("projectCount", projectCount);
			model.addAttribute("noOfPages", noOfPages);
			model.addAttribute("currentPage", currPage);

			List<ProjectDto> projects = this.projectDao.getProjectPerPage(currPage, RECORDS_PER_PAGE);
			model.addAttribute("projects", projects);

			List<ProjectDto> allProjects = new ArrayList<>();

			// allProjects.addAll(projectDao.getAllProjectDtos());
			// model.addAttribute("allProjects", allProjects);

			return "show-all-projects";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "redirect:error";
		}
	}
}

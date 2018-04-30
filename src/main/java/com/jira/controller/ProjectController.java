package com.jira.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
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
import com.jira.dao.UserDao;
import com.jira.dto.ProjectDto;
import com.jira.dto.ProjectTypeBusinessDto;
import com.jira.dto.ProjectTypeSoftwareDto;
import com.jira.dto.TaskBasicViewDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.model.Project;
import com.jira.model.ProjectCategory;
import com.jira.model.ProjectType;
import com.jira.model.User;

@Controller
public class ProjectController {
	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Autowired
	private ProjectCategoryDao projectCategoryDao;


	@Autowired
	private UserDao userDao;
	

	@Autowired
	private TaskDao taskDao;
	
	@RequestMapping(value = "/submitProject", method = RequestMethod.GET)
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

	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	public String create(Model model, @RequestParam String projectName, @RequestParam String projectType,
			@RequestParam String projectCategory,HttpSession session) throws ServletException, IOException {
		try {
			projectDao.isValidProjectName(projectName);
			int projTypeId = Integer.parseInt(projectType);
			int projCategoryId = Integer.parseInt(projectCategory);
			User user = (User) session.getAttribute("user");

			Project p = projectDao.getProject(projectName, projCategoryId, projTypeId, user.getId());

			projectDao.saveProject(p);
			return "redirect:./showAllProjects";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(value = "/showAllProjects", method = RequestMethod.GET)
	public String showAllProjects(Model model) {
		List<ProjectDto> allProjects = new ArrayList<>();

		try {
			allProjects.addAll(projectDao.getAllProjectDtos());
			model.addAttribute("allProjects", allProjects);
			return "show-all-projects";
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
	public String viewPatka(Model model, @PathVariable int id) {
		try {
			ProjectDto dtoProject = projectDao.getProjectDtoById(id);
			
			model.addAttribute("dtoProject",dtoProject);
			
			List<TaskBasicViewDto> tasksDto = taskDao.getAllByProjectId(id);
			
			model.addAttribute("tasksDto", tasksDto);
			return "project-view";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		
	}

}

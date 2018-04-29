package com.jira.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.jira.dao.ProjectDao;
import com.jira.dto.ProjectDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.ProjectException;
import com.jira.exception.UserDataException;

@Controller
public class RestController {

	@Autowired
	ProjectDao projectDao;

	@RequestMapping(value = "/searchProjects", method = RequestMethod.GET)
	@ResponseBody
	public String searchAutoComplete(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			String prefix = request.getParameter("prefix");

			System.out.println("Data from ajax call " + prefix);
			List<String> list =  projectDao.getLimitedProjectNamesWithPrefix(prefix);
			
			String projectNames = new Gson().toJson(list);
			response.setContentType("application/json");
			return projectNames;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/searchBtn", method = RequestMethod.POST)
	public String getSearch(Model model,@RequestParam String project) {
		try {
			ProjectDto dto = projectDao.getProjectDtoByName(project);
			
			model.addAttribute("dto", dto);
			return "project-view";
		} catch (DatabaseException | SQLException | ProjectException | UserDataException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}


}

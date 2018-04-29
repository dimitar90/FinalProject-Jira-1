package com.jira.controller;

import java.sql.SQLException;
import java.util.ArrayList;

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

	@RequestMapping(value = "/searchAutoComplete", method = RequestMethod.GET)
	@ResponseBody
	public String searchAutoComplete(HttpServletRequest request, HttpServletResponse response,Model model) {

		response.setContentType("application/json");
		try {
			String term = request.getParameter("term");

			System.out.println("Data from ajax call " + term);
			ArrayList<String> list = (ArrayList<String>) projectDao.getProjectNamesWithPrefix(term);

			// List<String> projects = new ArrayList<>();
			// List<String> projectName =
			// ProjectDao.getInstance().getProjectNamesWithPrefix(prefix);
			// projects.addAll(projectName);
			//
			String searchList = new Gson().toJson(list);
			return searchList;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/searchBtn", method = RequestMethod.POST)
	public String getSearch(Model model,@RequestParam String project) {
//		String projectName = request.getParameter("project");
		try {
			ProjectDto dto = projectDao.getProjectDtoByName(project);
			
			model.addAttribute("dto", dto);
			return "project-view";
//			request.getRequestDispatcher("WEB-INF/jsp/project-view.jsp").forward(request, response);
		} catch (DatabaseException | SQLException | ProjectException | UserDataException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}


}

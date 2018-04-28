package com.jira.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.exception.DatabaseException;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskFormDataWithFile;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	private static final String PATH_IMAGE_PREFFIX = "D:\\images\\tasks";

	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskIssueDao taskIssueDao;
	private final IProjectDao projectDao;
	private final IUserDao userDao;
	private final ITaskDao taskDao;

	@Autowired
	public TaskController(ITaskPriorityDao taskPriorityDao, ITaskIssueDao taskIssueDao, IProjectDao projectDao,
			IUserDao userDao, ITaskDao taskDao) {
		this.taskPriorityDao = taskPriorityDao;
		this.taskIssueDao = taskIssueDao;
		this.projectDao = projectDao;
		this.userDao = userDao;
		this.taskDao = taskDao;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String submit(ModelMap modelMap) {
		try {
			Collection<User> users = userDao.getAll();
			List<TaskPriority> priorities = taskPriorityDao.getAll();
			List<TaskIssue> issueTypes = taskIssueDao.getAll();
			//List<Project> projects = projectDao.getAllProjects();
			
			modelMap.addAttribute("priorities", priorities);
			modelMap.addAttribute("issueTypes", issueTypes);
			//request.addAttribute("projects", projects);
			modelMap.addAttribute("assignees", users);
			
			return "create-task";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public String submit( 	@RequestParam MultipartFile file, 
							@RequestParam String summary,
							@RequestParam String description, 
							@RequestParam String dueDate, 
							//@RequestParam Integer projectId, 
							@RequestParam Integer priorityId, 
							@RequestParam Integer issueTypeId, 
							@RequestParam Integer assigneeId, 
							ModelMap modelMap) {
		
		System.out.println(summary);
		System.out.println(dueDate);
		//System.out.println(projectId);
		System.out.println(priorityId);
		System.out.println(issueTypeId);
		System.out.println(assigneeId);
		System.out.println(description);
		try {
			System.out.println(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "show-all-tasks";
	}
}

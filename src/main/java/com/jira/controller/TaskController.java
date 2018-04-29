package com.jira.controller;

import static org.mockito.Matchers.booleanThat;

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
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jira.dao.TaskDao;
import com.jira.dto.TaskBasicViewDto;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	private static final int TASKS_COUNT_ON_PAGE = 3;
	private static final int FIRST_PAGE = 0;
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/all/{pageNumber}")
	public String showAllTasks(Model model, @PathVariable Integer pageNumber) {
		//TODO validation for logged user
	
		try {
			int countOfTasks = this.taskDao.getCountOfTasks();
			int noOfPages = countOfTasks / TASKS_COUNT_ON_PAGE;
			if (countOfTasks % TASKS_COUNT_ON_PAGE != 0) {
				noOfPages++;
			}
			model.addAttribute("noOfPages", noOfPages);
			model.addAttribute("currentPage", pageNumber);
			
			List<TaskBasicViewDto> tasks = this.taskDao.getByCurrentPageNumberAndTaskPerPage(pageNumber, TASKS_COUNT_ON_PAGE);
			model.addAttribute("tasks", tasks);

			return "show-all-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String submit(Model modelMap, HttpSession session) {
		try {
			Collection<User> users = userDao.getAll();
			Collection<Project> projects = projectDao.getAllProjects();
			List<TaskPriority> priorities = taskPriorityDao.getAll();
			List<TaskIssue> issueTypes = taskIssueDao.getAll();
			
			session.setAttribute("priorities", priorities);
			session.setAttribute("issueTypes", issueTypes);
			session.setAttribute("assignees", users);
			session.setAttribute("projects", projects);

			return "create-task";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public String submit(@RequestParam MultipartFile[] files, 
							@RequestParam String summary,
							@RequestParam String description, 
							@RequestParam String dueDate, 
							@RequestParam Integer projectId, 
							@RequestParam Integer priorityId, 
							@RequestParam Integer issueTypeId, 
							@RequestParam Integer assigneeId, 
							ModelMap modelMap) throws Exception {
		if (!this.isValidData(summary, description,dueDate,priorityId,issueTypeId,assigneeId, projectId, files)) {
			return "create-task";
		}
		
		System.out.println(summary);
		System.out.println(dueDate);
		System.out.println(projectId);
		System.out.println(priorityId);
		System.out.println(issueTypeId);
		System.out.println(assigneeId);
		System.out.println(description);
		
		for (MultipartFile f : files) {
			if (f.isEmpty()) {
				continue;
			}
			try {
				System.out.println(f.getInputStream().toString());
				System.out.println("IMA FAIL");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "redirect:./all/0";
	}

	private boolean isValidData(String summary, String description, String dueDate, Integer priorityId,
			Integer issueTypeId, Integer assigneeId, Integer projectId, MultipartFile[] files) throws Exception {
		boolean isValid = !summary.isEmpty() && 
						  !description.isEmpty() &&
						  !dueDate.isEmpty() &&
						  taskPriorityDao.isExistById(priorityId) &&
						  taskIssueDao.isExistById(issueTypeId) && 
						  userDao.isExistById(assigneeId) &&
						  projectDao.isExistById(projectId);
		
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			System.out.println("proverka");
		}
		
		return isValid;
	}
}

package com.jira.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.ITaskStateDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	private static final String REDIRECT_FIRST_PAGE_ALL_TASKS = "redirect:../../tasks/all/0";
	private static final int ROWS_COUNT_OF_PAGE = 3;
	
	private static final String PATH_IMAGE_PREFFIX = "D:\\images\\tasks";

	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskIssueDao taskIssueDao;
	private final IProjectDao projectDao;
	private final IUserDao userDao;
	private final ITaskDao taskDao;
	private final ITaskStateDao taskStateDao;

	@Autowired
	public TaskController(ITaskPriorityDao taskPriorityDao, ITaskIssueDao taskIssueDao, IProjectDao projectDao,
			IUserDao userDao, ITaskDao taskDao, ITaskStateDao taskStateDao) {
		this.taskPriorityDao = taskPriorityDao;
		this.taskIssueDao = taskIssueDao;
		this.projectDao = projectDao;
		this.userDao = userDao;
		this.taskDao = taskDao;
		this.taskStateDao = taskStateDao;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/usertasks")
	public String showTasksOnLoggedUser(HttpSession session, Model model) {
		//TODO validation for logged user
		User loggedUser = (User) session.getAttribute("user");
		if (loggedUser == null) {
			return "redirect:../../Jira/login";
		}
		try {
			model.addAttribute("issueTypes", this.taskIssueDao.getAll());
			List<TaskBasicViewDto> tasks = this.taskDao.getAllOpenTasksByUserId(loggedUser.getId());
			model.addAttribute("tasks", tasks);

			return "filtered-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "redirect:error";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/filter")
	public String getFilteredTasks(Model model, HttpServletRequest request) throws DatabaseException {
		String[] selectedIssueTypes = request.getParameterValues("selectedIssueTypeIds");
		String firstDate = request.getParameter("firstDate");
		String secondDate = request.getParameter("secondDate");
		
		List<Integer> issueTypeIds = this.getIssueTypeIds(selectedIssueTypes);
		
		List<TaskBasicViewDto> tasks = taskDao.getFilteredTasksByIssueTypeAndDate(issueTypeIds, firstDate, secondDate);
		model.addAttribute("tasks", tasks);
		model.addAttribute("issueTypes", taskIssueDao.getAll());
		return "filtered-tasks";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{taskId}")
	public String deleteTaskPost(Model model, @PathVariable("taskId") int taskId, HttpServletRequest request) {
		//TODO validate for logged user
		
		try {
			if (!this.taskDao.isExistById(taskId)) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			TaskViewDetailsDto task = this.taskDao.getById(taskId);
		    
			if (task == null) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			int loggedUserId = user.getId();
			TaskViewDetailsDto editTaskDto = taskDao.getById(taskId);

			if (editTaskDto.getAssignee().getId() != loggedUserId && editTaskDto.getCreator().getId() != loggedUserId) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			taskDao.deleteById(taskId);
			
	        return "redirect:" + REDIRECT_FIRST_PAGE_ALL_TASKS;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/edit/{taskId}")
	public String editTask(Model model, @PathVariable Integer taskId, HttpServletRequest request) {
		//TODO validate for logged user
		
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			int loggedUserId = user.getId();
			TaskViewDetailsDto editTaskDto = taskDao.getById(taskId);

			if (editTaskDto.getAssignee().getId() != loggedUserId && editTaskDto.getCreator().getId() != loggedUserId) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			if (!this.taskDao.isExistById(taskId)) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			model.addAttribute("states", taskStateDao.getAll());
			model.addAttribute("editTaskDto", editTaskDto);
			
			return "edit-task";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit/{taskId}")
	public String editTaskPost(Model model, @PathVariable Integer taskId, HttpServletRequest request) {
		//TODO validate for logged user
		
		try {
			TaskViewDetailsDto task = this.taskDao.getById(taskId);
		    int newStateId = Integer.parseInt(request.getParameter("newStateId"));
			
			if (task == null || !this.taskStateDao.isExistById(newStateId)) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			taskDao.changeStateById(taskId, newStateId);
			
	        return "redirect:/tasks/detail/" + taskId;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/detail/{taskId}")
	public String showTaskDetailById(Model model, @PathVariable Integer taskId) {
		try {
			TaskViewDetailsDto task = this.taskDao.getById(taskId);
			if (task == null) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}
			
			model.addAttribute("task", task);
			return "task-details";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all/{pageNumber}")
	public String showAllTasks(Model model, @PathVariable Integer pageNumber) {
		//TODO validation for logged user
		
		try {
			int countOfTasks = this.taskDao.getCountOfTasks();
			int noOfPages = (countOfTasks / ROWS_COUNT_OF_PAGE) - 1;
			if (countOfTasks % ROWS_COUNT_OF_PAGE != 0) {
				noOfPages++;
			}
			
			model.addAttribute("noOfPages", noOfPages);
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("issueTypes", this.taskIssueDao.getAll());
			
			List<TaskBasicViewDto> tasks = this.taskDao.getByCurrentPageNumberAndTaskPerPage(pageNumber, ROWS_COUNT_OF_PAGE);
			model.addAttribute("tasks", tasks);

			return "show-all-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "redirect:error";
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
							ModelMap modelMap,
							HttpServletRequest request) throws Exception {
		if (!this.isValidData(summary, description,dueDate,priorityId,issueTypeId,assigneeId, projectId, files)) {
			return "create-task";
		}
		
		String startDate = LocalDate.now().toString();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Task task = new Task(summary, dueDate, startDate, description, projectId, priorityId, issueTypeId, user.getId(), assigneeId);
		this.taskDao.saveTask(task);
		
//		for (MultipartFile f : files) {
//			if (f.isEmpty()) {
//				continue;
//			}
//			try {
//				System.out.println(f.getInputStream().toString());
//				System.out.println("IMA FAIL");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
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
	
	private List<Integer> getIssueTypeIds(String[] selectedIssueTypes) {
		List<Integer> issueTypeIds = new ArrayList<>();
		
		if (selectedIssueTypes == null) {
			issueTypeIds.addAll(this.taskIssueDao.getAllIds());
		} else {
			for (String issueId : selectedIssueTypes) {
				issueTypeIds.add(Integer.parseInt(issueId));
			}
		}
		
		return issueTypeIds;
	}
}

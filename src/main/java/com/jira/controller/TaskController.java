package com.jira.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.configuration.SpringWebConfig;
import com.jira.configuration.WebInitializer;
import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.ITaskStateDao;
import com.jira.interfaces.IUserDao;
import com.jira.manager.UserManager;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final String REDIRECT_FIRST_PAGE_ALL_TASKS = "redirect:../../tasks/all/0";
	private static final int ROWS_COUNT_PER_PAGE = 3;
	private static final String ALLOWED_FILE_EXTENSIONS = ".png .jpeg .jpg .bmp";
	private static final String MY_OPEN_TASK_TABLE_NAME = "MY OPEN TASKS";
	private static final String FILTERED_TASK_TABLE_NAME = "FILTERED TASKS";

	private static final String TASK_DUE_DATE_EXCEPTION_MESSAGE = "Due date can not be before today's date!";
	
	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskIssueDao taskIssueDao;
	private final IProjectDao projectDao;
	private final IUserDao userDao;
	private final ITaskDao taskDao;
	private final ITaskStateDao taskStateDao;
	private final UserManager userManager;
	
	@Autowired
	public TaskController(ITaskPriorityDao taskPriorityDao, ITaskIssueDao taskIssueDao, IProjectDao projectDao,
			IUserDao userDao, ITaskDao taskDao, ITaskStateDao taskStateDao, UserManager userManager) {
		this.taskPriorityDao = taskPriorityDao;
		this.taskIssueDao = taskIssueDao;
		this.projectDao = projectDao;
		this.userDao = userDao;
		this.taskDao = taskDao;
		this.taskStateDao = taskStateDao;
		this.userManager = userManager;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/usertasks")
	public String showTasksOnLoggedUser(HttpSession session, Model model) {
		User loggedUser = this.userManager.getLoggedUser(session);
		if (loggedUser == null) {
			return "redirect:../../Jira/login";
		}
		
		try {
			model.addAttribute("issueTypes", this.taskIssueDao.getAll());
			List<TaskBasicViewDto> tasks = this.taskDao.getAllOpenTasksByUserId(loggedUser.getId());
			model.addAttribute("tasks", tasks);
			model.addAttribute("tableName", MY_OPEN_TASK_TABLE_NAME);
			
			return "my-open-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/filter")
	public String getFilteredTasks(Model model, HttpServletRequest request) throws DatabaseException {
		try {
			String[] selectedIssueTypes = request.getParameterValues("selectedIssueTypeIds");
			String firstDate = request.getParameter("firstDate");
			String secondDate = request.getParameter("secondDate");

			List<Integer> issueTypeIds = this.getIssueTypeIds(selectedIssueTypes);
			List<TaskBasicViewDto> tasks = taskDao.getFilteredTasksByIssueTypeAndDate(issueTypeIds, firstDate, secondDate);
			
			model.addAttribute("tasks", tasks);
			model.addAttribute("issueTypes", taskIssueDao.getAll());
			model.addAttribute("tableName", FILTERED_TASK_TABLE_NAME);

			return "filtered-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/searchByName")
	public String searchByName(Model model, HttpServletRequest request) {
		try {
			String searchPart = request.getParameter("search");
			List<TaskBasicViewDto> tasks = this.taskDao.getTasksWhichIncludePartOfSearchStringInName(searchPart);
			model.addAttribute("tasks", tasks);
			model.addAttribute("issueTypes", taskIssueDao.getAll());
			model.addAttribute("tableName", FILTERED_TASK_TABLE_NAME);

			return "filtered-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/{taskId}")
	public String deleteTaskPost(Model model, @PathVariable("taskId") int taskId, HttpServletRequest request, HttpSession session) {
		User user = this.userManager.getLoggedUser(session);
		
		if (user == null) {
			return "redirect:../../login";
		}
		
		try {
			if (!this.taskDao.isExistById(taskId)) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}

			TaskViewDetailsDto task = this.taskDao.getById(taskId);

			if (task == null) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}

			int loggedUserId = user.getId();
			if (task.getAssignee().getId() != loggedUserId && task.getCreator().getId() != loggedUserId) {
				return REDIRECT_FIRST_PAGE_ALL_TASKS;
			}

			taskDao.deleteById(taskId);
			return  REDIRECT_FIRST_PAGE_ALL_TASKS;
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/edit/{taskId}")
	public String editTask(Model model, @PathVariable Integer taskId, HttpServletRequest request, HttpSession session) {
		User loggedUser = this.userManager.getLoggedUser(session);
		
		if (loggedUser == null) {
			return "redirect:../../login";
		}
		try {
			int loggedUserId = loggedUser.getId();
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
	public String editTaskPost(Model model, @PathVariable Integer taskId, HttpServletRequest request, HttpSession session) {
		User loggedUser = this.userManager.getLoggedUser(session);
		if (loggedUser == null) {
			return "redirect:../../login";
		}
		
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

	@RequestMapping(method = RequestMethod.POST, value = "/goToPage")
	public String goToPage(Model model, @RequestParam Integer page) throws DatabaseException {
		int countOfTasks = this.taskDao.getCountOfTasks();

		if (page <= 0 || page > this.getNumberOfPages(countOfTasks)) {
			page = 1;
		}
		
		return "redirect:./all/" + (page - 1);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/{pageNumber}")
	public String showAllTasks(Model model, @PathVariable Integer pageNumber, HttpSession session) {
		try {
			int countOfTasks = this.taskDao.getCountOfTasks();
			int noOfPages = this.getNumberOfPages(countOfTasks);
			

			model.addAttribute("currentRowsOfPage", ROWS_COUNT_PER_PAGE);
			model.addAttribute("countOfTasks", countOfTasks);
			model.addAttribute("noOfPages", noOfPages);
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("issueTypes", this.taskIssueDao.getAll());

			List<TaskBasicViewDto> tasks = this.taskDao.getByCurrentPageNumberAndTaskPerPage(pageNumber,
					ROWS_COUNT_PER_PAGE);
			model.addAttribute("tasks", tasks);

			return "show-all-tasks";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
	
	private int getNumberOfPages(int countOfTasks) {
		int noOfPages = (countOfTasks / ROWS_COUNT_PER_PAGE) - 1;
		if (countOfTasks % ROWS_COUNT_PER_PAGE != 0) {
			noOfPages++;
		}
		
		return noOfPages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String submit(Model modelMap, HttpSession session) {
		User loggedUser = this.userManager.getLoggedUser(session);
		if (loggedUser == null) {
			return "redirect:../login";
		}
		
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
						 HttpServletRequest request, 
						 HttpSession session) throws Exception {
		
		User loggedUser = this.userManager.getLoggedUser(session);
		if (loggedUser == null) {
			return "redirect:../../login";
		}
		
		if (!this.isValidData(summary, description, dueDate, priorityId, issueTypeId, assigneeId, projectId, files)) {
			return "create-task";
		}

		String startDate = LocalDate.now().toString();
		Task task = new Task(summary, dueDate, startDate, description, projectId, priorityId, issueTypeId, loggedUser.getId(),
				assigneeId);

		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			
			try {
				this.taskDao.saveFileToDisk(task, file, randomUUIDString);
			} catch (IOException e) {
				e.printStackTrace();
				modelMap.addAttribute("exception", e);
				return "error";
			}
		}

		this.taskDao.saveTask(task);
		return "redirect:./all/0";
	}

	private boolean isValidData(String summary, String description, String dueDate, Integer priorityId, Integer issueTypeId, Integer assigneeId, Integer projectId, MultipartFile[] files) throws Exception {
		boolean isValid = !summary.isEmpty() && 
						  !description.isEmpty() && 
						  !dueDate.isEmpty() &&
						  projectId != null && projectDao.isExistById(projectId) &&
						  priorityId != null && taskPriorityDao.isExistById(priorityId) && 
						  issueTypeId != null && taskIssueDao.isExistById(issueTypeId) && 
						  assigneeId != null && userDao.isExistById(assigneeId) && projectDao.isExistById(projectId);
		
		if ((LocalDate.parse(dueDate, DATE_FORMATTER).compareTo(LocalDate.now()) < 0)) {
			throw new TaskException(TASK_DUE_DATE_EXCEPTION_MESSAGE);
		}
		
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			
			if (file.getSize() > SpringWebConfig.MAX_FILE_SIZE_IN_BYTES) {
				return false;
			}
			
			String[] fileParts = file.getOriginalFilename().split("\\.");
			String fileExtension = fileParts[fileParts.length - 1];
			if (!ALLOWED_FILE_EXTENSIONS.contains(fileExtension)) {
				return false;
			}
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

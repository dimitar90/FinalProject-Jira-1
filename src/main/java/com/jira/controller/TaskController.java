package com.jira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskIssueDao taskIssueDao;

	@Autowired
	public TaskController(ITaskPriorityDao taskPriorityDao, ITaskIssueDao taskIssueDao) {
		this.taskPriorityDao = taskPriorityDao;
		this.taskIssueDao = taskIssueDao;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public String addTask(Model model) {
		List<TaskPriority> priorities = taskPriorityDao.getAll();
		List<TaskIssue> issueTypes = taskIssueDao.getAll();

		model.addAttribute("priorities", priorities);
		model.addAttribute("issueTypes", issueTypes);
		
		return "create-task";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public String addTaskPost(Model model) {
		List<TaskPriority> priorities = taskPriorityDao.getAll();
		List<TaskIssue> issueTypes = taskIssueDao.getAll();

		model.addAttribute("priorities", priorities);
		model.addAttribute("issueTypes", issueTypes);
		
		return "create-task";
	}
}

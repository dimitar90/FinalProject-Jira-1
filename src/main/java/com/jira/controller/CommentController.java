package com.jira.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.jira.dto.CommentViewDto;
import com.jira.exception.CommentException;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.ICommentTaskDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.IUserDao;
import com.jira.manager.UserManager;
import com.jira.model.CommentTask;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
	private static final String EMPTY_DESCRIPTION_ERROR_MESSAGE = "Description cannot be empty!";
	private static final String NOT_LOGGED_USER = "Logged first!";
	private static final String NOT_EXIST_TASK_MESSAGE = "This task not exist!";

	private final ITaskDao taskDao;
	private final ICommentTaskDao commentTaskDao;
	private final IUserDao userDao;
	private final UserManager userManager;
	@Autowired
	public CommentController(ITaskDao taskDao, ICommentTaskDao commentTaskDao, IUserDao userDao, UserManager userManager) {
		this.taskDao = taskDao;
		this.commentTaskDao = commentTaskDao;
		this.userDao = userDao;
		this.userManager = userManager;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String add(@RequestParam String description,
					  @RequestParam Integer taskId,
					  HttpSession session) throws Exception {
		User loggedUser = this.userManager.getLoggedUser(session);
		if (loggedUser == null) {
			throw new UserDataException(NOT_LOGGED_USER);
		}
		
		if (!this.taskDao.isExistById(taskId)) {
			throw new CommentException(NOT_EXIST_TASK_MESSAGE);
		}
		
		if (description.isEmpty()) {
			throw new CommentException(EMPTY_DESCRIPTION_ERROR_MESSAGE);
		}
		
		CommentTask comment = new CommentTask(description, LocalDateTime.now(), loggedUser.getId(), taskId);
		this.commentTaskDao.save(comment);
		
		CommentViewDto commentViewDto = new CommentViewDto(comment.getDescription(), comment.getDateTime(), this.userDao.getUserById(comment.getUserId()));
		
		return  new Gson().toJson(commentViewDto);
	}
}

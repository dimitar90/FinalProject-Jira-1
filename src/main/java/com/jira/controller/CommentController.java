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

	@Autowired
	public CommentController(ITaskDao taskDao, ICommentTaskDao commentTaskDao, IUserDao userDao) {
		this.taskDao = taskDao;
		this.commentTaskDao = commentTaskDao;
		this.userDao = userDao;
	}


	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String add(@RequestParam String description,
					  @RequestParam Integer taskId,
					  HttpSession session) throws Exception {
		
		if (!this.taskDao.isExistById(taskId)) {
			throw new CommentException(NOT_EXIST_TASK_MESSAGE);
		}
		
		User loggedUser = (User) session.getAttribute("user");
		if (loggedUser == null) {
			throw new UserDataException(NOT_LOGGED_USER);
		}
		
		if (description.isEmpty()) {
			throw new CommentException(EMPTY_DESCRIPTION_ERROR_MESSAGE);
		}
		
		CommentTask comment = new CommentTask(description, LocalDateTime.now(), loggedUser.getId(), taskId);
		this.commentTaskDao.save(comment);
		CommentViewDto commentViewDto = new CommentViewDto(comment.getDescription(), comment.getDateTime(), this.userDao.getUserById(comment.getUserId()));
		
		return new Gson().toJson(commentViewDto);
	}
}

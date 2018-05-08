package com.jira.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.jira.interfaces.ICommentDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.IUserDao;
import com.jira.manager.UserManager;
import com.jira.model.Comment;
import com.jira.model.User;
import com.jira.util.ImageConvertor;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
	private static final String EMPTY_DESCRIPTION_ERROR_MESSAGE = "Description cannot be empty!";
	private static final String NOT_LOGGED_USER = "Logged first!";
	private static final String NOT_EXIST_TASK_MESSAGE = "This task not exist!";
	private static final String SUCCESSFULLY_ADD_COMMENT_MESSAGE = "User %s with id: %d successfully added comment with id: %d to task with id: %d";
	
    private static final Logger logger = LogManager.getLogger(TaskController.class);
	private final ITaskDao taskDao;
	private final ICommentDao commentDao;
	private final UserManager userManager;
	
	@Autowired
	public CommentController(ITaskDao taskDao, ICommentDao commentTaskDao, UserManager userManager) {
		this.taskDao = taskDao;
		this.commentDao = commentTaskDao;
		this.userManager = userManager;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String add(@RequestParam String description,
					  @RequestParam Integer taskId,
					  HttpSession session,
					  Model model) throws Exception {
		try {
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
			
			Comment comment = new Comment(description, LocalDateTime.now(), loggedUser.getId(), taskId);
			this.commentDao.save(comment);
			//String userImageBase64 = ImageConvertor.convertFromLocalPathToBase64String(loggedUser.getImageUrl());
			String userAvatarName = loggedUser.getImageUrl().substring(loggedUser.getImageUrl().lastIndexOf("\\") + 1);
			
			CommentViewDto commentViewDto = new CommentViewDto(comment.getDescription(), comment.getDateTime(), loggedUser.getName(), userAvatarName);
			
			logger.info(String.format(SUCCESSFULLY_ADD_COMMENT_MESSAGE, loggedUser.getName(), loggedUser.getId(), comment.getId(), taskId));
			return  new Gson().toJson(commentViewDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			logger.error(e);
			return "error";
		}
	}
}

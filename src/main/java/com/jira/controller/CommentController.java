package com.jira.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.exception.CommentException;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.ICommentTaskDao;
import com.jira.interfaces.ITaskDao;
import com.jira.model.CommentTask;
import com.jira.model.User;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
	private static final String REDIRECT_TO_TASK_DETAIL = "redirect:../../tasks/detail/";

	private final ITaskDao taskDao;
	private final ICommentTaskDao commentTaskDao;

	@Autowired
	public CommentController(ITaskDao taskDao, ICommentTaskDao commentTaskDao) {
		this.taskDao = taskDao;
		this.commentTaskDao = commentTaskDao;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add/{taskId}")
	public String add(@PathVariable("taskId") Integer taskId, 
					 @RequestParam String description, HttpSession session) throws DatabaseException, CommentException {
		if (!this.taskDao.isExistById(taskId)) {
			return "redirect:../../tasks/all/0";
		}
		
		User loggedUser = (User) session.getAttribute("user");
		if (loggedUser == null) {
			return REDIRECT_TO_TASK_DETAIL + taskId;
		}
		
		if (description.isEmpty()) {
			return REDIRECT_TO_TASK_DETAIL + taskId;
		}
		
		CommentTask comment = new CommentTask(description, LocalDateTime.now(), loggedUser.getId(), taskId);
		this.commentTaskDao.save(comment);
		

		return REDIRECT_TO_TASK_DETAIL + taskId;
	}
}

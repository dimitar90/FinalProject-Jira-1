package com.jira.interfaces;

import org.springframework.stereotype.Component;

import com.jira.exception.CommentException;
import com.jira.model.CommentTask;

@Component
public interface ICommentTaskDao {
	public void save(CommentTask comment) throws CommentException;
}

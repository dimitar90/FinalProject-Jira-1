package com.jira.interfaces;

import com.jira.exception.CommentException;
import com.jira.model.CommentTask;

public interface ICommentTaskDao {
	public void save(CommentTask comment) throws CommentException;
}

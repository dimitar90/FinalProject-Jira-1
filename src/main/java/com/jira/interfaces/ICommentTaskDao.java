package com.jira.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.dto.CommentViewDto;
import com.jira.exception.CommentException;
import com.jira.exception.DatabaseException;
import com.jira.model.CommentTask;

@Component
public interface ICommentTaskDao {
	public void save(CommentTask comment) throws CommentException;
	
	public List<CommentViewDto> getCommentsByTaskId(int taskId) throws DatabaseException;
	
	public int getCommentsCountByTaskId(int taskId) throws DatabaseException;
}

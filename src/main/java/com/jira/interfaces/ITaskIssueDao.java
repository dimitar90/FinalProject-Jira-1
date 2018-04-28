package com.jira.interfaces;

import java.util.List;

import com.jira.model.TaskIssue;

public interface ITaskIssueDao {
	public List<TaskIssue> getAll();
	
	public TaskIssue getById(int issueId);
	
	public List<Integer> getIdsByNames(String[] selectedIssueTypes);
}

package com.jira.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.model.TaskIssue;

@Component
public interface ITaskIssueDao {
	public List<TaskIssue> getAll();
	
	public TaskIssue getById(int issueId);
	
	public List<Integer> getIdsByNames(String[] selectedIssueTypes);

	public boolean isExistById(Integer issueTypeId);
}

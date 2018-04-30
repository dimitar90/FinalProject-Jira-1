package com.jira.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.jira.model.TaskIssue;

@Component
public interface ITaskIssueDao {
	public List<TaskIssue> getAll();
	
	public TaskIssue getById(int issueId);
	
	public boolean isExistById(Integer issueTypeId);

	public Set<Integer> getAllIds();
}

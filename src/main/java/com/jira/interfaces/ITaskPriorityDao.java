package com.jira.interfaces;

import java.util.List;

import com.jira.model.TaskPriority;

public interface ITaskPriorityDao {
	public List<TaskPriority> getAll();
	
	public TaskPriority getById(int priorityId);
}

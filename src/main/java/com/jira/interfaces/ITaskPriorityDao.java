package com.jira.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.model.TaskPriority;

@Component
public interface ITaskPriorityDao {
	public List<TaskPriority> getAll();
	
	public TaskPriority getById(int priorityId);
}

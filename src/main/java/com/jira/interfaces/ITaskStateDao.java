package com.jira.interfaces;

import java.util.List;

import com.jira.model.TaskState;

public interface ITaskStateDao {
	public TaskState getById(int stateId);
	
	public List<TaskState> getAll();
}

package com.jira.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.model.TaskState;

@Component
public interface ITaskStateDao {
	public TaskState getById(int stateId);
	
	public List<TaskState> getAll();
}

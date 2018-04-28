package com.jira.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.dto.TaskViewDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.model.Task;

@Component
public interface ITaskDao {
	//public void saveTask(Task task) throws DatabaseException, SQLException;
	
	//public TaskViewDto getById(int taskId) throws DatabaseException;
	
	//public List<TaskViewDto> getAll() throws UserDataException, DatabaseException;
	
	//public void changeStateById(int taskId, int newStateId) throws DatabaseException;
	
	//public void deleteById(Integer taskId) throws DatabaseException;
	
	//public List<TaskViewDto> getAllByProjectId(int pId) throws DatabaseException, UserDataException;
	
	//public List<TaskViewDto> getTasksByIssueTypeIds(List<Integer> selectedIssueTypeIds)
	//		throws DatabaseException, UserDataException;
	
	//public List<TaskViewDto> getAllOpenTasksByUserId(int userId) throws DatabaseException;
	
	//public List<TaskViewDto> getTasksBetweenTwoDates(String firstDate, String secondDate)
	//		throws DatabaseException;
}

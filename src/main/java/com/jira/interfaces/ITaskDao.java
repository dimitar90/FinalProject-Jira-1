package com.jira.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.exception.UserDataException;
import com.jira.model.Task;

@Component
public interface ITaskDao {
	public void saveTask(Task task) throws DatabaseException, SQLException;
	
	public TaskViewDetailsDto getById(int taskId) throws DatabaseException;
	
	public void changeStateById(int taskId, int newStateId) throws DatabaseException;
	
	public void deleteById(Integer taskId) throws DatabaseException;
	
	public List<TaskViewDetailsDto> getAllByProjectId(int pId) throws Exception;
	
	public List<TaskViewDetailsDto> getTasksByIssueTypeIds(List<Integer> selectedIssueTypeIds) throws  Exception;
	
	public List<TaskViewDetailsDto> getAllOpenTasksByUserId(int userId) throws DatabaseException;
	
	public List<TaskViewDetailsDto> getTasksBetweenTwoDates(String firstDate, String secondDate) throws DatabaseException;

	public int getCountOfTasks() throws DatabaseException;

	public List<TaskBasicViewDto> getByCurrentPageNumberAndTaskPerPage(Integer pageNumber, int tasksCountOnPage) throws DatabaseException, Exception;

	public boolean isExistById(Integer taskId) throws DatabaseException;
}
